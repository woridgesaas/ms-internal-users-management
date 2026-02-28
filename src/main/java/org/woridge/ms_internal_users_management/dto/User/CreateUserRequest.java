package org.woridge.ms_internal_users_management.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

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
    @Email(message = "Email format is invalid")
    @Size(max = 50, message = "Email must not exceed 50 characters")
    private String email;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9+ ]{7,15}$", message = "Phone number format is invalid")
    private String phoneNumber;
    @NotBlank(message = "CIN is required")
    @Pattern(regexp = "^[A-Za-z0-9]{4,10}$", message = "CIN must be between 4 and 10 alphanumeric characters")
    private String CIN;
    @NotBlank(message = "Department name is required")
    @Size(max = 50, message = "Department name must not exceed 50 characters")
    private String departmentName;
    @NotBlank(message = "Post name is required")
    @Size(max = 50, message = "Post name must not exceed 50 characters")
    private String PostName;
    @Size(max = 500, message = "Picture path must not exceed 500 characters")
    private String picturePath;
}


