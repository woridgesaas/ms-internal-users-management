package org.woridge.ms_internal_users_management.dto.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.woridge.ms_internal_users_management.enumeration.Status;

@Getter
@Setter
public class UpdateDepartmentRequest {

    @NotBlank(message = "Department name is required")
    @Size(message = "Department name must not exceed 50 characters", max = 50)
    private String name;

    @NotNull(message = "Department status is required")
    private Status status;
}