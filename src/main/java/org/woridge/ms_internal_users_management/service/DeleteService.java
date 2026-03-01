package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.SuccessResponse;

import java.util.UUID;

public interface DeleteService {
     SuccessResponse<Void> softDeleteUser(UUID userId);
}
