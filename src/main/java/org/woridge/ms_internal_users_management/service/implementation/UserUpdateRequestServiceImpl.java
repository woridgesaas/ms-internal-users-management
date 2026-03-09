package org.woridge.ms_internal_users_management.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.user.CreateUserUpdateRequest;
import org.woridge.ms_internal_users_management.dto.user.HRUserUpdateRequest;
import org.woridge.ms_internal_users_management.dto.user.HRUserUpdateRequestDetails;
import org.woridge.ms_internal_users_management.enumeration.RequestStatus;
import org.woridge.ms_internal_users_management.exception.user.*;
import org.woridge.ms_internal_users_management.mapper.UserUpdateRequestMapper;
import org.woridge.ms_internal_users_management.model.RequestFieldChange;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.model.UserUpdateRequest;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.repository.UserUpdateRequestRepository;
import org.woridge.ms_internal_users_management.service.UserService;
import org.woridge.ms_internal_users_management.service.UserUpdateChangeDetector;
import org.woridge.ms_internal_users_management.service.UserUpdateRequestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class UserUpdateRequestServiceImpl implements UserUpdateRequestService {

    private final UserUpdateRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserUpdateChangeDetector userUpdateChangeDetector;

    @Override
    @Transactional
    public void submitRequest(UUID userId, CreateUserUpdateRequest dto) {

        validateNoPendingRequest(userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        // 1 detect changes
        List<RequestFieldChange> changes = userUpdateChangeDetector.detect(user, dto);
        if (changes.isEmpty()) {
            throw new NoUserUpdateChangesException("No changes detected in the update request");
        }
        // 2 map entity
        UserUpdateRequest request =
                UserUpdateRequestMapper.toEntity(user, changes);

        requestRepository.save(request);
    }

    @Override
    public List<HRUserUpdateRequest> getAllRequests() {

        List<UserUpdateRequest> requests = requestRepository.findAll();

        return requests.stream()
                .map(request -> {

                    User user = userRepository.findById(request.getUserId())
                            .orElseThrow(() -> new UserNotFoundException("User not found"));

                    return UserUpdateRequestMapper.toListDTO(request, user);
                })
                .toList();
    }

    @Override
    public HRUserUpdateRequestDetails getRequestDetails(UUID requestId) {

        UserUpdateRequest request = getRequestOrThrow(requestId);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return UserUpdateRequestMapper.toDetailsDTO(request, user);
    }

    @Override
    @Transactional
    public void approveRequest(UUID requestId, UUID hrId) {

        UserUpdateRequest request = getRequestOrThrow(requestId);

        validateRequestIsPending(request);

        User user = userRepository.findWithFullProfileById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        applyChanges(user, request);

        userRepository.save(user);

        request.setStatus(RequestStatus.APPROVED);
        request.setProcessedBy(hrId);
        request.setProcessedAt(LocalDateTime.now());

        requestRepository.save(request);
    }

    @Override
    @Transactional
    public void rejectRequest(UUID requestId, UUID hrId) {

        UserUpdateRequest request = getRequestOrThrow(requestId);

        validateRequestIsPending(request);

        request.setStatus(RequestStatus.REJECTED);
        request.setProcessedBy(hrId);
        request.setProcessedAt(LocalDateTime.now());

        requestRepository.save(request);
    }
    public UUID getCurrentUserId() {

        String employeeCin = (String) SecurityContextHolder.getContext() .getAuthentication() .getPrincipal();
        UUID userId = userService.findIdByCIN(employeeCin);
        return userId;
    }

    private void applyChanges(User user, UserUpdateRequest request) {

        for (RequestFieldChange change : request.getFieldChanges()) {

            BiConsumer<User, String> applier =
                    fieldAppliers.get(change.getFieldName());

            if (applier != null) {
                applier.accept(user, change.getNewValue());
            }
        }
    }

    private final Map<String, BiConsumer<User, String>> fieldAppliers = Map.of(

            "firstName", (user, value) -> user.setFirstName(value),

            "lastName", (user, value) -> user.setLastName(value),

            "cin", (user, value) -> user.setCIN(value),

            "email", (user, value) -> user.setEmail(value),

            "phone", (user, value) -> user.setPhoneNumber(value),

            "address", (user, value) -> user.setAddress(value),

            "profileImage", (user, value) -> user.setPicturePath(value),

            "birthDate", (user, value) ->
                    user.setBirthDate(value == null ? null : java.time.LocalDate.parse(value))
    );

    private void validateNoPendingRequest(UUID userId) {

        boolean exists = requestRepository.existsByUserIdAndStatus(userId, RequestStatus.PENDING);

        if (exists) {
            throw new PendingRequestAlreadyExistsException("User already has a pending update request");
        }
    }

    private void validateRequestIsPending(UserUpdateRequest request) {

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new RequestAlreadyProcessedException("Request already processed");
        }
    }

    private UserUpdateRequest getRequestOrThrow(UUID requestId) {

        return requestRepository.findById(requestId)
                .orElseThrow(() -> new UpdateRequestNotFoundException("Update request not found"));
    }
}