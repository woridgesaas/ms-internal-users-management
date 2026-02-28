package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.User.CreateUserRequest;
import org.woridge.ms_internal_users_management.dto.User.CreateUserResponse;

public interface UserService {

    CreateUserResponse createUser(CreateUserRequest request);
}
