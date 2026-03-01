package org.woridge.ms_internal_users_management.dto.User;

import java.util.UUID;

public record UserSearchResponse(
        UUID id,
        String cin,
        String fullName,
        String departmentName
) {}