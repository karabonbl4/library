package com.library.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    private final RabbitProperties rabbitProperties;

    @Bean
    public Queue queue() {
        return new Queue(rabbitProperties.getQueue());
    }

    @Bean
    public Queue exceptionQueue() {
        return new Queue(rabbitProperties.getExceptionQueue());
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(rabbitProperties.getExchange());
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(rabbitProperties.getRoutingKey());
    }

    @Bean
    public Binding exceptionBinding() {
        return BindingBuilder
                .bind(exceptionQueue())
                .to(exchange())
                .with(rabbitProperties.getRoutingExceptionKey());
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}