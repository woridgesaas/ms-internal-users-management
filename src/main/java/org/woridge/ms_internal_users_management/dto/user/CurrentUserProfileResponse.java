package org.woridge.ms_internal_users_management.dto.user;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CurrentUserProfileResponse(
        UUID id,
        String fullName,
        String email,
        String picturePath
) {
}
