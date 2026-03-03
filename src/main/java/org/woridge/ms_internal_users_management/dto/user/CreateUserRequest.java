package org.woridge.ms_internal_users_management.dto.user;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateUserRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;
    @NotBlank(message = "role name is required")
    @Size(max = 30, message = "role name must not exceed 20 characters")
    private String role;
    @NotBlank(message = "Email is required")
   // @Email(message = "Email format is invalid")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Email format is invalid"
    )
    @Size(max = 50, message = "Email must not exceed 50 characters")
    private String email;
    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^(\\+212|0)[5-7][0-9]{8}$",
            message = "Numéro marocain invalide"
    )    private String phoneNumber;
    @NotBlank(message = "CIN is required")
    @Pattern(
            regexp = "^[A-Za-z]{1,2}[0-9]{4,8}$",
            message = "CIN invalide (ex: AB123456)"
    )    private String CIN;
    @NotNull(message = "Department is required")
    private UUID departmentId;
    @NotNull(message = "Post is required")
    private UUID postId;
    @Size(max = 500, message = "Picture path must not exceed 500 characters")
    private String picturePath;
}


