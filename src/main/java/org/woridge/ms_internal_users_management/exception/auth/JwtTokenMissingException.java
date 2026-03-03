package org.woridge.ms_internal_users_management.exception.auth;

public class JwtTokenMissingException extends RuntimeException {
    public JwtTokenMissingException(String message) {
        super(message);
    }
}
