package org.woridge.ms_internal_users_management.dto.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.woridge.ms_internal_users_management.enumeration.StatusDepartment;

@Getter
@Setter
public class UpdateDepartmentRequest {

    @NotBlank
    @Size(max = 50)
    private String name;
    private StatusDepartment status;
}