package org.woridge.ms_internal_users_management.exception.auth;

public class AuthFailedException extends RuntimeException {

    public AuthFailedException(String message) {
        super(message);
    }
}
