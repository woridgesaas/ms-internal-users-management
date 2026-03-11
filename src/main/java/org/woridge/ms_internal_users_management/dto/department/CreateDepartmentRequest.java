package org.woridge.ms_internal_users_management.dto.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.woridge.ms_internal_users_management.enumeration.Status;

@Getter
@Setter
public class CreateDepartmentRequest {

    @NotBlank//utilisée pour garantir qu'une chaîne de caractères  n'est ni nulle, ni vide, ni composée uniquement d'espaces
    @Size(max = 50)
    private String name;

    @NotNull
    private Status status;
}
