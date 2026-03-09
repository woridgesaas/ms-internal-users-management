package org.woridge.ms_internal_users_management.mapper;

import org.woridge.ms_internal_users_management.dto.user.CreateUserUpdateRequest;
import org.woridge.ms_internal_users_management.dto.user.FieldChange;
import org.woridge.ms_internal_users_management.dto.user.HRUserUpdateRequest;
import org.woridge.ms_internal_users_management.dto.user.HRUserUpdateRequestDetails;
import org.woridge.ms_internal_users_management.model.RequestFieldChange;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.model.UserUpdateRequest;
import org.woridge.ms_internal_users_management.service.UserUpdateChangeDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserUpdateRequestMapper {

    private UserUpdateRequestMapper() {}

    // Create UserUpdateRequest entity with field changes

    public static UserUpdateRequest toEntity(
            User user,
            List<RequestFieldChange> changes
    ) {

        UserUpdateRequest request = new UserUpdateRequest();
        request.setUserId(user.getId());

        for (RequestFieldChange change : changes) {
            change.setRequest(request);
        }

        request.setFieldChanges(changes);

        return request;
    }
    /*
     * LIST DTO (for HR table)
     */
    public static HRUserUpdateRequest toListDTO(UserUpdateRequest entity, User user) {

        return new HRUserUpdateRequest(
                entity.getId(),
                entity.getUserId(),
                user.getFirstName() + " " + user.getLastName(),
                entity.getRequestedAt(),
                entity.getFieldChanges().size(),
                entity.getStatus()
        );
    }

    /*
     * DETAILS DTO (for HR view)
     */
    public static HRUserUpdateRequestDetails toDetailsDTO(UserUpdateRequest entity, User user) {

        List<FieldChange> changes = entity.getFieldChanges()
                .stream()
                .map(UserUpdateRequestMapper::toFieldChangeDTO)
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

    /*
     * Convert RequestFieldChange entity to DTO
     */
    private static FieldChange toFieldChangeDTO(RequestFieldChange change) {

        return new FieldChange(
                change.getFieldName(),
                change.getOldValue(),
                change.getNewValue()
        );
    }

}