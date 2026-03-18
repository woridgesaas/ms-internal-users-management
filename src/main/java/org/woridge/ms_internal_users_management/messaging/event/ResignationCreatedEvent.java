package org.woridge.ms_internal_users_management.messaging.event;


import lombok.Builder;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ResignationCreatedEvent(
        UUID   resignationId,
        String employeeEmail,
        String employeeFullName,
        String employeeCin,
        LocalDate resignationDate,
        String reason,
        String  companyName

) {}