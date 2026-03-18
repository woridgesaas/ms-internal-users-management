package org.woridge.ms_internal_users_management.messaging.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.messaging.event.CreateUserNotificationEvent;
import org.woridge.ms_internal_users_management.model.User;

@Service
@RequiredArgsConstructor
public class NotificationEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.notification}")
    private String exchange;

    @Value("${rabbitmq.routing.user-created}")
    private String routingKey;

    public void publishUserCreated(User user, String password) {

        CreateUserNotificationEvent event =
                CreateUserNotificationEvent.builder()
                        .fullName(user.getFirstName() + " " + user.getLastName())
                        .email(user.getEmail())
                        .password(password)
                        .build();

        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}