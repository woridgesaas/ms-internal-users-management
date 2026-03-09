package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.user.CreateUserUpdateRequest;
import org.woridge.ms_internal_users_management.dto.user.HRUserUpdateRequest;
import org.woridge.ms_internal_users_management.dto.user.HRUserUpdateRequestDetails;

import java.util.List;
import java.util.UUID;

public interface UserUpdateRequestService {

    void submitRequest(UUID userId, CreateUserUpdateRequest dto);

    List<HRUserUpdateRequest> getAllRequests();

    HRUserUpdateRequestDetails getRequestDetails(UUID requestId);

    void approveRequest(UUID requestId, UUID hrId);

    void rejectRequest(UUID requestId, UUID hrId);

     UUID getCurrentUserId();

}