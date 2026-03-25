package org.woridge.ms_internal_users_management.messaging.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.messaging.event.CreateUserNotificationEvent;
import org.woridge.ms_internal_users_management.messaging.event.HolidayCreatedEvent;
import org.woridge.ms_internal_users_management.messaging.event.ResignationCreatedEvent;
import org.woridge.ms_internal_users_management.model.Holiday;
import org.woridge.ms_internal_users_management.model.User;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class NotificationEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.notification}")
    private String exchange;

    @Value("${rabbitmq.routing.user-created}")
    private String routingKey;

    @Value("${rabbitmq.routing.holiday-created}")
    private String holidayRoutingKey;

    @Value("${rabbitmq.routing.resignation-created}")
    private String resignationRoutingKey;

    public void publishUserCreated(User user, String password) {

        CreateUserNotificationEvent event =
                CreateUserNotificationEvent.builder()
                        .fullName(user.getFirstName() + " " + user.getLastName())
                        .email(user.getEmail())
                        .password(password)
                        .build();

        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }

    public void publishResignationCreated(
            String resignationId,
            User user,
            LocalDate resignationDate,
            String reason,
            String companyName) {

        ResignationCreatedEvent event = ResignationCreatedEvent.builder()
                .resignationId(resignationId)
                .employeeEmail(user.getEmail())
                .employeeFullName(user.getFirstName() + " " + user.getLastName())
                .employeeCin(user.getCIN())
                .resignationDate(resignationDate)
                .reason(reason)
                .companyName(companyName)
                .build();

        rabbitTemplate.convertAndSend(exchange, resignationRoutingKey, event);
    }

    public void publishHolidayCreated(Holiday holiday) {

        HolidayCreatedEvent event = HolidayCreatedEvent.builder()
                .date(holiday.getDate())
                .build();

        rabbitTemplate.convertAndSend(exchange, holidayRoutingKey, event);
    }
}