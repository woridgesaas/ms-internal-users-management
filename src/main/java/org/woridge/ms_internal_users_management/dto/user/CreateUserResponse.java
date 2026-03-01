package org.woridge.ms_internal_users_management.dto.user;

import lombok.Builder;
import lombok.Getter;
import org.woridge.ms_internal_users_management.enumeration.Status;

import java.util.UUID;

@Getter
@Builder
public class CreateUserResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String roleName;
    private String email;
    private String phoneNumber;
    private String CIN;
    private Status status;
    private String address;
    private String picturePath;
    private String departmentName;
    private String postName;
}

