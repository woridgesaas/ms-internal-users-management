package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.education.AddEducationRequest;
import org.woridge.ms_internal_users_management.dto.education.UpdateEducationRequest;
import org.woridge.ms_internal_users_management.model.Education;

import java.util.UUID;

public interface EducationValidator {

    void validateEducationForCreation(UUID userId, AddEducationRequest addEducationRequest);

    void validateEducationForUpdate(UUID userId, Education education, UpdateEducationRequest updateEducationRequest);
}
