package org.woridge.ms_internal_users_management.dto.department;

import lombok.Builder;
import lombok.Data;
import org.woridge.ms_internal_users_management.enumeration.Status;

import java.util.UUID;

@Builder//// Pattern Builder utilisé pour construire facilement des objets DepartmentResponse
@Data
public class DepartmentResponse {

    private UUID  id;
    private String name;
    private Status status;
    private long noOfEmployees;
}