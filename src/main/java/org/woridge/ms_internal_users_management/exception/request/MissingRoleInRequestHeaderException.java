package org.woridge.ms_internal_users_management.exception.request;

public class MissingRoleInRequestHeaderException extends RuntimeException {

    public MissingRoleInRequestHeaderException(String message) {

        super(message);
    }
}
