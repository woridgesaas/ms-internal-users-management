package org.woridge.ms_internal_users_management.dto.profile_update_request;

import lombok.Builder;

@Builder
public record FieldChangeSummary(
        String fieldName,
        String oldValue,
        String newValue
) {
}
