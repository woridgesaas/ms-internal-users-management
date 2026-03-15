package org.woridge.ms_internal_users_management.dto.post;

import jakarta.validation.constraints.NotNull;
import org.woridge.ms_internal_users_management.enumeration.Status;

import java.util.UUID;

public record UpdatePostStatusRequest(

        @NotNull(message = "Post id is required")
        UUID id,

        @NotNull(message = "Post status is required")
        Status status
) {
}
