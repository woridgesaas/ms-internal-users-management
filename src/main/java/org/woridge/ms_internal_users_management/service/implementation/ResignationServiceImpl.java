package org.woridge.ms_internal_users_management.service.implementation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.resignation.ResignationCreateRequest;
import org.woridge.ms_internal_users_management.dto.resignation.ResignationResponse;
import org.woridge.ms_internal_users_management.dto.resignation.ResignationUpdateRequest;
import org.woridge.ms_internal_users_management.exception.resignation.ResignationAlreadyExistsException;
import org.woridge.ms_internal_users_management.exception.resignation.ResignationNotFoundException;
import org.woridge.ms_internal_users_management.exception.user.UserNotFoundException;
import org.woridge.ms_internal_users_management.mapper.ResignationMapper;
import org.woridge.ms_internal_users_management.config.RabbitMQConfig;
import org.woridge.ms_internal_users_management.messaging.event.ResignationCreatedEvent;
import org.woridge.ms_internal_users_management.messaging.publisher.NotificationEventPublisher;
import org.woridge.ms_internal_users_management.model.Resignation;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.repository.ResignationRepository;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.ResignationService;

import java.util.List;
import java.util.UUID;
@Service
@AllArgsConstructor
@Transactional
public class ResignationServiceImpl implements ResignationService {

    private final ResignationRepository      resignationRepository;
    private final UserRepository             userRepository;
    private final ResignationMapper          resignationMapper;
    private final NotificationEventPublisher notificationEventPublisher;

    // Ajouter dans les dépendances de la classe
    private final jakarta.servlet.http.HttpServletRequest
            httpServletRequest;

    @Override
    public ResignationResponse create(
            ResignationCreateRequest request,
            String companyName) {

        User user = userRepository
                .findByCIN(request.cin())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with CIN: "
                                        + request.cin()
                        )
                );

        if (resignationRepository.existsByUser(user)) {
            throw new ResignationAlreadyExistsException(
                    "Resignation already exists"
            );
        }

        Resignation saved = resignationRepository.save(
                resignationMapper.toEntity(request, user)
        );

        // ── Event notification email (existant) ──
        notificationEventPublisher
                .publishResignationCreated(
                        saved.getId().toString(),
                        user,
                        saved.getResignationDate(),
                        saved.getReason(),
                        companyName
                );

        // ── Event chatbot ──
        // companyName = X-Subdomain = tenant
        notificationEventPublisher
                .publishResignationChatbot(
                        user.getId().toString(),
                        saved.getResignationDate(),
                        companyName
                );

        return resignationMapper.toResponse(saved);
    }
    @Override
    public ResignationResponse getById(UUID id) {
        return resignationMapper.toResponse(findResignationById(id));
    }

    @Override
    public List<ResignationResponse> getAll() {
        return resignationRepository.findAll()
                .stream()
                .map(resignationMapper::toResponse)
                .toList();
    }

    @Override
    public ResignationResponse update(UUID id, ResignationUpdateRequest request) {
        var resignation = findResignationById(id);
        resignationMapper.updateEntity(resignation, request);
        return resignationMapper.toResponse(resignationRepository.save(resignation));
    }

    @Override
    public void delete(UUID id) {
        resignationRepository.delete(findResignationById(id));
    }

    private Resignation findResignationById(UUID id) {
        return resignationRepository.findById(id)
                .orElseThrow(() -> new ResignationNotFoundException(
                        "Resignation not found with id: " + id));
    }
}