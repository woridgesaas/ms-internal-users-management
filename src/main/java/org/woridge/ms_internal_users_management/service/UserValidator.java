package org.woridge.ms_internal_users_management.service;

import java.util.UUID;

public interface UserValidator {

    void validateUser(UUID userId);

    void validateUserForCreation(String email, String CIN);

    void validateUserForUpdate(UUID userId, String email, String CIN);
}

