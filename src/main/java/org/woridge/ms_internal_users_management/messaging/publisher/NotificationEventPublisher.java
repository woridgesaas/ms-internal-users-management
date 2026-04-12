package org.woridge.ms_internal_users_management.messaging.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.messaging.event.*;
import org.woridge.ms_internal_users_management.model.Holiday;
import org.woridge.ms_internal_users_management.model.User;

import java.math.BigDecimal;
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
//pour synchroniser ms chatbot avec ms internal user management
    // ── Routing key chatbot ──
    @Value("${rabbitmq.routing.chatbot-sync}")
    private String chatbotSyncRoutingKey;

    // ── Publier pour ms-chatbot uniquement ──
    public void publishUserChatbotSync(
            User user,
            String tenant) {

        String poste = (user.getPost() != null)
                ? user.getPost().getName()
                : "";

        String departement =
                (user.getDepartment() != null)
                        ? user.getDepartment().getName()
                        : "";

        UserChatbotSyncEvent event =
                UserChatbotSyncEvent.builder()
                        .userId(user.getId().toString())
                        .nom(user.getLastName())
                        .prenom(user.getFirstName())
                        .email(user.getEmail())
                        .departement(departement)
                        .poste(poste)
                        .role(user.getRole().name())
                        .tenant(tenant)
                        .build();

        rabbitTemplate.convertAndSend(
                exchange,
                chatbotSyncRoutingKey,
                event,
                message -> {
                    message.getMessageProperties()
                            .setContentType("application/json");
                    return message;
                }
        );
    }


    // ── Exchanges chatbot ──
    @Value("${rabbitmq.exchange.chatbot-resignation}")
    private String chatbotResignationExchange;

    @Value("${rabbitmq.exchange.chatbot-salary}")
    private String chatbotSalaryExchange;

    // ── Routing keys chatbot ──
    @Value("${rabbitmq.routing.chatbot-resignation}")
    private String chatbotResignationRoutingKey;

    @Value("${rabbitmq.routing.chatbot-salary}")
    private String chatbotSalaryRoutingKey;

    // ── Publier resignation pour ms-chatbot ──
    public void publishResignationChatbot(
            String userId,
            LocalDate dateDemission,
            String tenant) {

        ResignationChatbotEvent event =
                ResignationChatbotEvent.builder()
                        .userId(userId)
                        .dateDemission(dateDemission)
                        .tenant(tenant)
                        .build();

        rabbitTemplate.convertAndSend(
                chatbotResignationExchange,
                chatbotResignationRoutingKey,
                event,
                message -> {
                    message.getMessageProperties()
                            .setContentType("application/json");
                    return message;
                }
        );

        log.info(
                "ResignationChatbot publie — userId: {}",
                userId
        );
    }

    // ── Publier salaire pour ms-chatbot ──
    public void publishSalaireChatbot(
            String userId,
            BigDecimal montant,
            String mois,
            String tenant) {

        SalaireChatbotEvent event =
                SalaireChatbotEvent.builder()
                        .userId(userId)
                        .montant(montant)
                        .mois(mois)
                        .tenant(tenant)
                        .build();

        rabbitTemplate.convertAndSend(
                chatbotSalaryExchange,
                chatbotSalaryRoutingKey,
                event,
                message -> {
                    message.getMessageProperties()
                            .setContentType("application/json");
                    return message;
                }
        );

        log.info(
                "SalaireChatbot publie — userId: {} mois: {}",
                userId, mois
        );
    }
}