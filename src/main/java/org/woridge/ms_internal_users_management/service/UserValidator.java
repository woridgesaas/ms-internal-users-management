package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.model.User;

public interface UserValidator {
    void checkUniqueEmail(String email);
    void checkUniqueCIN(String cin);
    void validateUserProfile(User user);
}

