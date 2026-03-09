package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.user.CreateUserUpdateRequest;
import org.woridge.ms_internal_users_management.model.RequestFieldChange;
import org.woridge.ms_internal_users_management.model.User;

import java.util.List;

public interface UserUpdateChangeDetector {
     List<RequestFieldChange> detect(User user, CreateUserUpdateRequest dto);
}
