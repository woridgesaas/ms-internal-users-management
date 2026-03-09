package org.woridge.ms_internal_users_management.exception.user;

public class PendingRequestAlreadyExistsException extends RuntimeException {
    public PendingRequestAlreadyExistsException(String message) {
        super(message);
    }
}
