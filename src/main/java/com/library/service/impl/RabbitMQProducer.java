package com.library.service.impl;

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
        LOGGER.info(String.format("Message sent -> %s - is done!", methodName));
        rabbitTemplate.convertAndSend(exchange, routingKey, methodName);
    }

    @AfterReturning("callAtExceptionHandlerPublic()")
    public void sendUnhappyCaseMessage(JoinPoint joinPoint){
        String methodName = joinPoint.getStaticPart().toShortString();
        LOGGER.info(String.format("Message sent -> %s", methodName));
        rabbitTemplate.convertAndSend(exchange, routingUnhappyKey, methodName);
    }
}