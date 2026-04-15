package org.woridge.ms_internal_users_management.dto.profile_update_request;

import lombok.Builder;
import org.woridge.ms_internal_users_management.enumeration.RequestStatus;
import org.woridge.ms_internal_users_management.enumeration.SectionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record ProfileUpdateDetailsRequest(
        UUID requestId,//meme chose ila f dek voir detail bghat tsseft approve wla reject
        UUID userId,//hadi future ila drna f dek detail twta ela smiya dyl employe ydiha profil dyalh
        String employeeFullName,
        SectionType sectionType,
        UUID targetEntityId,
        RequestStatus status,
        LocalDateTime requestedAt,
        LocalDateTime processedAt,
        UUID processedBy,
        List<FieldChangeSummary> fieldChanges
) {
}