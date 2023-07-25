package com.library.service.impl;

import com.library.config.RabbitProperties;
import com.library.constant.MessageType;
import com.library.model.dto.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.library.constant.ApplicationConstant.DATE_TIME_FORMATTER;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitProducer {

    private final RabbitProperties rabbitProperties;

    private final RabbitTemplate rabbitTemplate;

    @Pointcut("execution(public * com.library.controller.BookController.*(..))")
    public void callAtBookControllerPublic() { }

    @Pointcut("execution(public * com.library.controller.DefaultExceptionHandler.*(..))")
    public void callAtExceptionHandlerPublic() { }


    @Before("callAtBookControllerPublic()")
    public void sendRequiredArguments(JoinPoint joinPoint){
        String s = Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(", "));
        LogMessage logMessage = buildMessage(joinPoint, s, MessageType.ARGS);
        log.debug(logMessage.toString());
        this.convertAndSend(logMessage);
    }

    @AfterReturning(pointcut = "callAtBookControllerPublic()", returning = "retVal")
    public void sendMessage(JoinPoint joinPoint, Object retVal){
        String s = retVal.toString();
        LogMessage logMessage = buildMessage(joinPoint, s, MessageType.RETURNS);
        log.debug(logMessage.toString());
        this.convertAndSend(logMessage);
    }

    @AfterReturning("callAtExceptionHandlerPublic()")
    public void sendException(JoinPoint joinPoint){
        Exception e = (Exception) Arrays.stream(joinPoint.getArgs())
                .findFirst()
                .get();
        LogMessage logMessage = buildMessage(joinPoint, e.getMessage(), MessageType.WARNING);
        log.debug(logMessage.toString());
        this.convertAndSend(logMessage);
    }

    private LogMessage buildMessage(JoinPoint joinPoint, String body, MessageType type){
        LogMessage logMessage = new LogMessage();
        logMessage.setTime(LocalDateTime.now().format(DATE_TIME_FORMATTER));
        logMessage.setExecutor(joinPoint.getStaticPart().getSignature().toString());
        logMessage.setType(type);
        logMessage.setMessage(body);
        return logMessage;
    }

    private void convertAndSend(LogMessage message){
        if (message.getType().equals(MessageType.WARNING)){
            rabbitTemplate.convertAndSend(rabbitProperties.getExchange(), rabbitProperties.getRoutingExceptionKey(), message);
        } else {
        rabbitTemplate.convertAndSend(rabbitProperties.getExchange(), rabbitProperties.getRoutingKey(), message);}
    }
}