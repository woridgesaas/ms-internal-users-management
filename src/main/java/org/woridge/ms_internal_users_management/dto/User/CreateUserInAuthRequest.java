package org.woridge.ms_internal_users_management.dto.User;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserInAuthRequest {

    private String cin;
    private String email;
    private String password;
    private String roleName;

}
