package org.woridge.ms_internal_users_management.dto.department;

import lombok.Builder;
import lombok.Data;
import org.woridge.ms_internal_users_management.enumeration.StatusDepartment;
import java.util.UUID;

@Builder
@Data
public class DepartmentResponse {

    private UUID  id;
    private String name;
    private StatusDepartment status;
    private Integer noOfEmployees; // ← ajoute ça
}