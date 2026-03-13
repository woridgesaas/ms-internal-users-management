package org.woridge.ms_internal_users_management.mapper;

import org.woridge.ms_internal_users_management.dto.user.FieldChange;
import org.woridge.ms_internal_users_management.dto.user.HRUserUpdateRequest;
import org.woridge.ms_internal_users_management.dto.user.HRUserUpdateRequestDetails;
import org.woridge.ms_internal_users_management.model.RequestFieldChange;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.model.UserUpdateRequest;

import java.util.List;

public class UserUpdateRequestMapper {

    public static UserUpdateRequest toUserUpdateRequest(User user, List<RequestFieldChange> changes) {

        UserUpdateRequest request = new UserUpdateRequest();
        request.setUserId(user.getId());

        for (RequestFieldChange change : changes) {
            change.setRequest(request);
        }

        request.setFieldChanges(changes);

        return request;
    }

    public static HRUserUpdateRequest toHRUserUpdateRequest(UserUpdateRequest entity, User user) {

        return new HRUserUpdateRequest(
                entity.getId(),
                entity.getUserId(),
                user.getFirstName() + " " + user.getLastName(),
                entity.getRequestedAt(),
                entity.getFieldChanges().size(),
                entity.getStatus()
        );
    }

    public static HRUserUpdateRequestDetails toHRUserUpdateRequestDetails(UserUpdateRequest entity, User user) {

        List<FieldChange> changes = entity.getFieldChanges()
                .stream()
                .map(UserUpdateRequestMapper::toFieldChange)
                .toList();

        return new HRUserUpdateRequestDetails(
                entity.getId(),
                entity.getUserId(),
                user.getFirstName() + " " + user.getLastName(),
                entity.getRequestedAt(),
                entity.getStatus(),
                changes
        );
    }


    // Convert RequestFieldChange entity to DTO

    private static FieldChange toFieldChange(RequestFieldChange change) {

        return new FieldChange(
                change.getFieldName(),
                change.getOldValue(),
                change.getNewValue()
        );
    }

}