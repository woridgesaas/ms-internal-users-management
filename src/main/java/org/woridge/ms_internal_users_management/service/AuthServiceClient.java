package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.model.User;

public interface AuthServiceClient {
    void createUserInAuthService(User user, String hashedPassword);
}

