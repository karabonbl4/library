package com.library.service.impl;

import com.library.model.dto.ResponseException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class RabbitMQProducer {

    @Value("${rabbitmq.routing.happy.key}")
    private String routingKey;

    @Value("${rabbitmq.routing.unhappy.key}")
    private String routingUnhappyKey;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    private final RabbitTemplate rabbitTemplate;

    @Pointcut("execution(public * com.library.controller.BookController.*(..))")
    public void callAtBookControllerPublic() { }

    @Pointcut("execution(public * com.library.controller.DefaultExceptionHandler.*(..))")
    public void callAtExceptionHandlerPublic() { }

    @AfterReturning("callAtBookControllerPublic()")
    public void sendHappyCaseMessage(JoinPoint joinPoint){
        String methodName = joinPoint.getStaticPart().toShortString();
        String message = methodName + " is done!";
        LOGGER.info(String.format("Message sent -> %s", message));
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    @AfterReturning("callAtExceptionHandlerPublic()")
    public void sendMessage(JoinPoint joinPoint){
        Exception e = (Exception) Arrays.stream(joinPoint.getArgs())
                .findFirst()
                .get();
        ResponseException errorResponse = new ResponseException(e.getMessage(), LocalDateTime.now());
        String message = e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse));
        LOGGER.info(String.format("Message sent -> %s", message));
        rabbitTemplate.convertAndSend(exchange, routingUnhappyKey, message);
    }
}