package org.woridge.ms_internal_users_management.dto.post;

import java.util.UUID;

public record PostResponse(
        UUID id,
        String name
) {}