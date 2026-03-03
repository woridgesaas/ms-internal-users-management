package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.user.CreateUserRequest;
import org.woridge.ms_internal_users_management.dto.user.CreateUserResponse;

import org.woridge.ms_internal_users_management.dto.user.UserSearchResponse;
import org.woridge.ms_internal_users_management.dto.user.GetAllUserResponse;

import java.util.List;

public interface UserService {

    CreateUserResponse createUser(CreateUserRequest request);
    UserSearchResponse searchByCin(String cin);
    List<GetAllUserResponse> getAllUsers();
}
