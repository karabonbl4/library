package com.library.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RabbitProperties {

    @Value("${rabbitmq.queue.log}")
    private String queue;

    @Value("${rabbitmq.queue.exception}")
    private String exceptionQueue;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.routing.exception.key}")
    private String routingExceptionKey;
}
