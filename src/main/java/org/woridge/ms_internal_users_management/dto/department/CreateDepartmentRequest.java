package org.woridge.ms_internal_users_management.dto.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDepartmentRequest {
    @NotBlank//used specifically on String fields to ensure the value is not null, not empty, and contains at least one non-whitespace characte
    @Size(max = 50)
    private String name;
}
