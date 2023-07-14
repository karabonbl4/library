package com.library.service.impl;

import com.library.config.RabbitProperties;
import com.library.model.dto.ResponseException;
import com.library.model.dto.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerTemplateAvailabilityProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.library.constant.ApplicationConstant.DATE_TIME_FORMATTER;

@Aspect
@Component
@RequiredArgsConstructor
public class RabbitProducer {

    private final RabbitProperties rabbitProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitProducer.class);

    private final RabbitTemplate rabbitTemplate;

    @Pointcut("execution(public * com.library.controller.BookController.*(..))")
    public void callAtBookControllerPublic() { }

    @Pointcut("execution(public * com.library.controller.DefaultExceptionHandler.*(..))")
    public void callAtExceptionHandlerPublic() { }


    @Before("callAtBookControllerPublic()")
    public void sendRequiredArguments(JoinPoint joinPoint){
        String s = Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(", "));
        String message = String.format("%s - %s - args: %s",LocalDateTime.now().format(DATE_TIME_FORMATTER), joinPoint.getStaticPart().getSignature(), s);
        LOGGER.debug(message);
        rabbitTemplate.convertAndSend(rabbitProperties.getExchange(), rabbitProperties.getRoutingKey(), message);
    }

    @AfterReturning(pointcut = "callAtBookControllerPublic()", returning = "retVal")
    public void sendMessage(JoinPoint joinPoint, Object retVal){
        String s = retVal.toString();
        String message = String.format("%s - %s - returns: %s",LocalDateTime.now().format(DATE_TIME_FORMATTER), joinPoint.getStaticPart().getSignature(), s);
        LOGGER.debug(message);
        rabbitTemplate.convertAndSend(rabbitProperties.getExchange(), rabbitProperties.getRoutingKey(), message);
    }

    @AfterReturning("callAtExceptionHandlerPublic()")
    public void sendException(JoinPoint joinPoint){
        Exception e = (Exception) Arrays.stream(joinPoint.getArgs())
                .findFirst()
                .get();
        ResponseException errorResponse = new ResponseException(e.getMessage(), LocalDateTime.now());
        String message = e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse));
        LOGGER.debug(String.format("Message sent -> %s", message));
        rabbitTemplate.convertAndSend(rabbitProperties.getExchange(), rabbitProperties.getRoutingExceptionKey(), message);
    }
}