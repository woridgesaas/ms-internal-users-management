package org.woridge.ms_internal_users_management.messaging.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Ces noms doivent être IDENTIQUES à ceux dans ms-notifications .NET
    public static final String EXCHANGE    = "woridge.exchange";
    public static final String QUEUE       = "resignation.notification.queue";
    public static final String ROUTING_KEY = "resignation.created";

    @Bean
    public TopicExchange resignationExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue resignationQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    public Binding resignationBinding(Queue resignationQueue,
                                      TopicExchange resignationExchange) {
        return BindingBuilder
                .bind(resignationQueue)
                .to(resignationExchange)
                .with(ROUTING_KEY);
    }

    // ── Convertisseur JSON avec support LocalDate ─────────────────────────
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());                        // ← support LocalDate
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);    // ← format "2026-04-30" et pas [2026,4,30]
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}