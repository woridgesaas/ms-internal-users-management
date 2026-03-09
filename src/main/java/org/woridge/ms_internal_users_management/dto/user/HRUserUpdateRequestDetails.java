package org.woridge.ms_internal_users_management.dto.user;

import org.woridge.ms_internal_users_management.enumeration.RequestStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record HRUserUpdateRequestDetails(

        UUID requestId,//meme chose ila f dek voir detail bghat tsseft approve wla reject
        UUID userId,//hadi future ila drna f dek detail twta ela smiya dyl employe ydiha profil dyalh
        String employeeName,
        LocalDateTime requestDate,
        RequestStatus status,
        List<FieldChange> changes

) {}
