package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.user.CreateUserRequest;
import org.woridge.ms_internal_users_management.dto.user.CreateUserResponse;

import org.woridge.ms_internal_users_management.dto.user.UserSearchResponse;
import org.woridge.ms_internal_users_management.dto.user.GetAllUserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    CreateUserResponse createUser(CreateUserRequest request);
    UserSearchResponse searchByCin(String cin);
    List<GetAllUserResponse> getAllUsers();
    UUID findIdByCIN(String cin);
}
