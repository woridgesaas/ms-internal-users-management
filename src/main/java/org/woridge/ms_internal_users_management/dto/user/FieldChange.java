package org.woridge.ms_internal_users_management.dto.user;

public record FieldChange(

        String fieldName,
        String oldValue,
        String newValue

) {}
