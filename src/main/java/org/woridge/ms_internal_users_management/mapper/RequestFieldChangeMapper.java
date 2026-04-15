package org.woridge.ms_internal_users_management.mapper;

import org.woridge.ms_internal_users_management.dto.profile_update_request.FieldChangeSummary;
import org.woridge.ms_internal_users_management.model.RequestFieldChange;

public class RequestFieldChangeMapper {

    public static FieldChangeSummary toFieldChangeSummary(RequestFieldChange requestFieldChange) {

        return FieldChangeSummary
                .builder()
                .fieldName(requestFieldChange.getFieldName())
                .oldValue(requestFieldChange.getOldValue())
                .newValue(requestFieldChange.getNewValue())
                .build();
    }
}
