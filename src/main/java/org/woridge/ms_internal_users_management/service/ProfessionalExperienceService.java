package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.professional_experience.AddProfessionalExperienceRequest;
import org.woridge.ms_internal_users_management.dto.professional_experience.AddProfessionalExperienceResponse;
import org.woridge.ms_internal_users_management.dto.professional_experience.UpdateProfessionalExperienceRequest;

import java.util.UUID;

public interface ProfessionalExperienceService {

    AddProfessionalExperienceResponse addProfessionalExperience(UUID userId, AddProfessionalExperienceRequest addProfessionalExperienceRequest);

    void updateProfessionalExperience(UUID userId, UUID professionalExperienceId, UpdateProfessionalExperienceRequest updateProfessionalExperienceRequest);

    void deleteProfessionalExperience(UUID professionalExperienceId);
}
