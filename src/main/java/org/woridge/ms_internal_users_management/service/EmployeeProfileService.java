package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.user.EmployeeProfileResponse;

import java.util.UUID;

public interface EmployeeProfileService {
    EmployeeProfileResponse getEmployeeProfile(UUID userId);

}
