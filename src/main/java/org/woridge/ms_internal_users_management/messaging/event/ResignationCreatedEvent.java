package org.woridge.ms_internal_users_management.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResignationCreatedEvent {
    private String    resignationId;
    private String    employeeEmail;
    private String    employeeFullName;
    private String    employeeCin;
    private LocalDate resignationDate;
    private String    reason;
    private String    companyName;
}