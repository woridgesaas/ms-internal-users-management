package org.woridge.ms_internal_users_management.exception.auth;

public class NoAuthoritiesFoundException extends RuntimeException {

    public NoAuthoritiesFoundException(String message) {
        super(message);
    }
}
