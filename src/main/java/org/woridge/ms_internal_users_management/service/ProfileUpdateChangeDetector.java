package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.model.RequestFieldChange;

import java.util.List;

public interface ProfileUpdateChangeDetector<E, R> {

    List<RequestFieldChange> detectChanges(E currentEntity, R updateRequest);
}

