package org.woridge.ms_internal_users_management.service;

public interface UserValidator {
    void checkUniqueEmail(String email);
    void checkUniqueCIN(String cin);
}

