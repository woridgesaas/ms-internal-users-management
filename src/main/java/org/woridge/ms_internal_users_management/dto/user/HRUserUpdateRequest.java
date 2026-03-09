package org.woridge.ms_internal_users_management.dto.user;

import org.woridge.ms_internal_users_management.enumeration.RequestStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record HRUserUpdateRequest(

        UUID requestId, //nécessaire pour appeler APPROVED ou REJECTED
        UUID userId, //utile si HR veut voir le profil de l’employé (futur)
        String employeeName,
        LocalDateTime requestDate,
        int numberOfChanges,
        RequestStatus status

) {}
