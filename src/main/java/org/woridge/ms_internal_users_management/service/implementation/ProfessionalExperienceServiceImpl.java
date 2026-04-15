package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woridge.ms_internal_users_management.dto.professional_experience.AddProfessionalExperienceRequest;
import org.woridge.ms_internal_users_management.dto.professional_experience.AddProfessionalExperienceResponse;
import org.woridge.ms_internal_users_management.dto.professional_experience.UpdateProfessionalExperienceRequest;
import org.woridge.ms_internal_users_management.exception.professional_experience.ProfessionalExperienceIdNotMatchedException;
import org.woridge.ms_internal_users_management.exception.professional_experience.ProfessionalExperienceNotFoundException;
import org.woridge.ms_internal_users_management.exception.user.UserNotFoundException;
import org.woridge.ms_internal_users_management.mapper.ProfessionalExperienceMapper;
import org.woridge.ms_internal_users_management.model.ProfessionalExperience;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.repository.ProfessionalExperienceRepository;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.ProfessionalExperienceService;
import org.woridge.ms_internal_users_management.service.ProfessionalExperienceValidator;
import org.woridge.ms_internal_users_management.service.UserValidator;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class ProfessionalExperienceServiceImpl implements ProfessionalExperienceService {

    private final UserRepository userRepository;
    private final ProfessionalExperienceRepository professionalExperienceRepository;
    private final ProfessionalExperienceValidator professionalExperienceValidator;
    private final UserValidator userValidator;

    @Override
    public AddProfessionalExperienceResponse addProfessionalExperience(UUID userId, AddProfessionalExperienceRequest addProfessionalExperienceRequest) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with the following id : " + userId + " not found"));

        professionalExperienceValidator.validateProfessionalExperienceForCreation(userId, addProfessionalExperienceRequest);

        ProfessionalExperience professionalExperience = ProfessionalExperienceMapper.toProfessionalExperience(user, addProfessionalExperienceRequest);

        return ProfessionalExperienceMapper.toAddProfessionalExperienceResponse(professionalExperienceRepository.save(professionalExperience));
    }

    @Override
    public void updateProfessionalExperience(UUID userId, UUID professionalExperienceId, UpdateProfessionalExperienceRequest updateProfessionalExperienceRequest) {

        if (!professionalExperienceId.equals(updateProfessionalExperienceRequest.id())) {

            throw new ProfessionalExperienceIdNotMatchedException("Professional experience ids aren't matched");
        }

        userValidator.validateUser(userId);

        ProfessionalExperience currentProfessionalExperience = getCurrentProfessionalExperience(professionalExperienceId);

        professionalExperienceValidator.validateProfessionalExperienceForUpdate(userId, currentProfessionalExperience, updateProfessionalExperienceRequest);

        ProfessionalExperienceMapper.updateProfessionalExperience(currentProfessionalExperience, updateProfessionalExperienceRequest);

        professionalExperienceRepository.save(currentProfessionalExperience);
    }

    @Override
    public void deleteProfessionalExperience(UUID professionalExperienceId) {

        ProfessionalExperience currentProfessionalExperience = getCurrentProfessionalExperience(professionalExperienceId);

        professionalExperienceRepository.delete(currentProfessionalExperience);
    }

    // Helper method
    private ProfessionalExperience getCurrentProfessionalExperience(UUID professionalExperienceId) {

        return professionalExperienceRepository.findById(professionalExperienceId).orElseThrow(() ->
                new ProfessionalExperienceNotFoundException("Professional experience with id : " + professionalExperienceId + " not found"));
    }
}
