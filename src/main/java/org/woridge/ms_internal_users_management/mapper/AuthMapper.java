package org.woridge.ms_internal_users_management.mapper;

import org.woridge.ms_internal_users_management.dto.User.CreateUserInAuthRequest;
import org.woridge.ms_internal_users_management.model.User;

public class AuthMapper {

    // Transforme un User interne en CreateUserInAuthRequest pour le ms-auth
    public static CreateUserInAuthRequest toCreateUserInAuthRequest(User user, String hashedPassword) {
        return CreateUserInAuthRequest
                .builder()
                .cin(user.getCIN())
                .email(user.getEmail())
                .password(hashedPassword)
                .roleName(user.getRole().getRoleName())
                .build();
    }
}
