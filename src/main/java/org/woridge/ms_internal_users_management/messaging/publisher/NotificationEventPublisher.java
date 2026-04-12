package org.woridge.ms_internal_users_management.messaging.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.messaging.event.*;
import org.woridge.ms_internal_users_management.model.Holiday;
import org.woridge.ms_internal_users_management.model.User;

import java.time.LocalDate;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Service
@RequiredArgsConstructor
public class NotificationEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.notification}")
    private String exchange;

    @Value("${rabbitmq.routing.user-sync}")
    private String userSyncRoutingKey;

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
    public void publishUserSync(User user) {

        UserCreatedEvent event = new UserCreatedEvent(
                user.getCIN(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getDepartment().getId().toString(),
                user.getRole().name()
        );

        rabbitTemplate.convertAndSend(exchange, userSyncRoutingKey, event, message -> {
            message.getMessageProperties().setContentType("application/json");
            return message;
        });
    }
 //pour synchroniser ms interview avec ms internal user management
    @Value("${rabbitmq.routing.user-created-interview}")
    private String userCreatedInterviewRoutingKey;

    public void publishUserCreatedInterview(User user) {
        UserCreatedInterviewEvent event =
                UserCreatedInterviewEvent.builder()
                        .userId(user.getId().toString())
                        .fullName(user.getFirstName() + " " + user.getLastName())
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .cin(user.getCIN())
                        .departmentId(user.getDepartment() != null
                                ? user.getDepartment().getId().toString()
                                : null)
                        .build();

        rabbitTemplate.convertAndSend(exchange, userCreatedInterviewRoutingKey, event);
    }
    @Value("${rabbitmq.routing.user-created-document}")
    private String userCreatedDocumentRoutingKey;

    public void publishUserCreatedForDocument(User user) {

        UserCreatedForDocumentEvent event =
                new UserCreatedForDocumentEvent(
                        user.getId().toString(),
                        user.getCIN()
                );

        rabbitTemplate.convertAndSend(exchange, userCreatedDocumentRoutingKey, event);
    }

}