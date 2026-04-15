package org.woridge.ms_internal_users_management.dto.profile_update_request;

import lombok.Builder;
import org.woridge.ms_internal_users_management.enumeration.RequestStatus;
import org.woridge.ms_internal_users_management.enumeration.SectionType;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ProfileUpdateSummaryRequest(
        UUID requestId, //nécessaire pour appeler APPROVED ou REJECTED
        UUID userId, //utile si HR veut voir le profil de l’employé (futur)
        String employeeFullName,
        SectionType sectionType,
        UUID targetEntityId,
        int numberOfChanges,
        RequestStatus status,
        LocalDateTime requestedAt
) {
}