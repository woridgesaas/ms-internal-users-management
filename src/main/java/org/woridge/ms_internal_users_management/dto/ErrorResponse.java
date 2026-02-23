package org.woridge.ms_internal_users_management.dto;

import java.time.Instant;

public record ErrorResponse<T>(Instant timestamp, T message) {


    public static <T> ErrorResponse<T> createErrorResponse(T message) {
        return new ErrorResponse<>(Instant.now(), message);
    }
}
