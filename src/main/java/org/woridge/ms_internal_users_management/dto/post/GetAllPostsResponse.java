package org.woridge.ms_internal_users_management.dto.post;

import lombok.Builder;
import org.woridge.ms_internal_users_management.enumeration.Status;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record GetAllPostsResponse(
        UUID id,
        String name,
        BigDecimal baseSalary,
        Status status,
        Instant createdAt
) {
}