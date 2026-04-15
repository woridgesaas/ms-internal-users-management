package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.education.AddEducationRequest;
import org.woridge.ms_internal_users_management.dto.education.AddEducationResponse;
import org.woridge.ms_internal_users_management.dto.education.UpdateEducationRequest;

import java.util.UUID;

public interface EducationService {

    AddEducationResponse addEducation(UUID userId, AddEducationRequest addEducationRequest);

    void updateEducation(UUID userId, UUID educationId, UpdateEducationRequest updateEducationRequest);

    void deleteEducation(UUID educationId);
}
