package org.woridge.ms_internal_users_management.exception.auth;

public class UnauthenticatedUserException extends RuntimeException {

    public UnauthenticatedUserException(String message) {
        super(message);
    }
}
