package org.woridge.ms_internal_users_management.exception.profile_update_request;

public class PendingProfileUpdateRequestAlreadyExistsException extends RuntimeException {

    public PendingProfileUpdateRequestAlreadyExistsException(String message) {

        super(message);
    }
}
