package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.user.*;

import java.util.List;
import java.util.UUID;

public interface UserService {

    CreateUserResponse createUser(CreateUserRequest request);

    void updateUserPersonalInformation(UUID userId, UpdateUserPersonalInformationRequest updateUserPersonalInformationRequest);

    UserSearchResponse searchByCin(String cin);

    List<GetAllUserResponse> getAllUsers();

    CurrentUserProfileResponse getCurrentUserProfile(String CIN);

    UUID findIdByCIN(String cin);

    UserProfileResponse getUserProfile(UUID userId);
}
