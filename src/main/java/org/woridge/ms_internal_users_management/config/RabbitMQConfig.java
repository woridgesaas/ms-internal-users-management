package org.woridge.ms_internal_users_management.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.notification}")
    private String notificationExchange;

    @Value("${rabbitmq.queue.provisioning}")
    private String provisioningQueue;

    @Value("${rabbitmq.routing.tenant-ready}")
    private String tenantReadyRoutingKey;

    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(notificationExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);

        return rabbitTemplate;
    }

    @Bean
    public Queue provisioningQueue(){
        return QueueBuilder.durable(this.provisioningQueue)
                .build();
    }
    @Bean
    public TopicExchange provisioningExchange(){
        return ExchangeBuilder
                .topicExchange(this.notificationExchange)
                .durable(true)
                .build();
    }
    @Bean
    public Binding provisioningBinding(){
        return BindingBuilder
                .bind(provisioningQueue())
                .to(provisioningExchange())
                .with(this.tenantReadyRoutingKey);
    }
}