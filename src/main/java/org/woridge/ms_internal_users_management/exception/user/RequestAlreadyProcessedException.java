package org.woridge.ms_internal_users_management.exception.user;

public class RequestAlreadyProcessedException extends RuntimeException {
    public RequestAlreadyProcessedException(String message) {
        super(message);
    }
}
