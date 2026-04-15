package org.woridge.ms_internal_users_management.mapper;

import org.woridge.ms_internal_users_management.dto.professional_experience.AddProfessionalExperienceRequest;
import org.woridge.ms_internal_users_management.dto.professional_experience.AddProfessionalExperienceResponse;
import org.woridge.ms_internal_users_management.dto.professional_experience.ProfessionalExperienceSummary;
import org.woridge.ms_internal_users_management.dto.professional_experience.UpdateProfessionalExperienceRequest;
import org.woridge.ms_internal_users_management.model.ProfessionalExperience;
import org.woridge.ms_internal_users_management.model.User;

import java.util.List;
import java.util.Set;

public class ProfessionalExperienceMapper {

    public static ProfessionalExperience toProfessionalExperience(User user, AddProfessionalExperienceRequest addProfessionalExperienceRequest) {

        return ProfessionalExperience
                .builder()
                .employmentType(addProfessionalExperienceRequest.employmentType())
                .experienceLevel(addProfessionalExperienceRequest.experienceLevel())
                .companyName(addProfessionalExperienceRequest.companyName())
                .companyLocation(addProfessionalExperienceRequest.companyLocation())
                .jobTitle(addProfessionalExperienceRequest.jobTitle())
                .startDate(addProfessionalExperienceRequest.startDate())
                .endDate(addProfessionalExperienceRequest.endDate())
                .isCurrent(Boolean.TRUE.equals(addProfessionalExperienceRequest.isCurrent()))
                .description(addProfessionalExperienceRequest.description())
                .user(user)
                .build();
    }

    public static AddProfessionalExperienceResponse toAddProfessionalExperienceResponse(ProfessionalExperience professionalExperience) {

        return AddProfessionalExperienceResponse
                .builder()
                .id(professionalExperience.getId())
                .employmentType(professionalExperience.getEmploymentType())
                .experienceLevel(professionalExperience.getExperienceLevel())
                .companyName(professionalExperience.getCompanyName())
                .companyLocation(professionalExperience.getCompanyLocation())
                .jobTitle(professionalExperience.getJobTitle())
                .startDate(professionalExperience.getStartDate())
                .endDate(professionalExperience.getEndDate())
                .isCurrent(professionalExperience.getIsCurrent())
                .description(professionalExperience.getDescription())
                .build();
    }

    public static void updateProfessionalExperience(ProfessionalExperience currentProfessionalExperience, UpdateProfessionalExperienceRequest updateProfessionalExperienceRequest) {

        currentProfessionalExperience.setEmploymentType(updateProfessionalExperienceRequest.employmentType());
        currentProfessionalExperience.setExperienceLevel(updateProfessionalExperienceRequest.experienceLevel());
        currentProfessionalExperience.setCompanyName(updateProfessionalExperienceRequest.companyName());
        currentProfessionalExperience.setCompanyLocation(updateProfessionalExperienceRequest.companyLocation());
        currentProfessionalExperience.setJobTitle(updateProfessionalExperienceRequest.jobTitle());
        currentProfessionalExperience.setStartDate(updateProfessionalExperienceRequest.startDate());
        currentProfessionalExperience.setEndDate(updateProfessionalExperienceRequest.endDate());
        currentProfessionalExperience.setIsCurrent(Boolean.TRUE.equals(updateProfessionalExperienceRequest.isCurrent()));
        currentProfessionalExperience.setDescription(updateProfessionalExperienceRequest.description());
    }

    public static List<ProfessionalExperienceSummary> toProfessionalExperienceSummaries(Set<ProfessionalExperience> professionalExperiences) {

        return professionalExperiences
                .stream()
                .map(ProfessionalExperienceMapper::toProfessionalExperienceSummary)
                .toList();
    }

    public static ProfessionalExperienceSummary toProfessionalExperienceSummary(ProfessionalExperience professionalExperience) {

        return ProfessionalExperienceSummary
                .builder()
                .id(professionalExperience.getId())
                .employmentType(professionalExperience.getEmploymentType())
                .experienceLevel(professionalExperience.getExperienceLevel())
                .companyName(professionalExperience.getCompanyName())
                .companyLocation(professionalExperience.getCompanyLocation())
                .jobTitle(professionalExperience.getJobTitle())
                .startDate(professionalExperience.getStartDate())
                .endDate(professionalExperience.getEndDate())
                .isCurrent(professionalExperience.getIsCurrent())
                .description(professionalExperience.getDescription())
                .build();
    }
}