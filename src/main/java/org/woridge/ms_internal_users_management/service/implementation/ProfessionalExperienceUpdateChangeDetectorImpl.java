package org.woridge.ms_internal_users_management.service.implementation;

import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.professional_experience.UpdateProfessionalExperienceRequest;
import org.woridge.ms_internal_users_management.model.ProfessionalExperience;
import org.woridge.ms_internal_users_management.model.RequestFieldChange;
import org.woridge.ms_internal_users_management.service.ProfileUpdateChangeDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProfessionalExperienceUpdateChangeDetectorImpl implements ProfileUpdateChangeDetector<ProfessionalExperience, UpdateProfessionalExperienceRequest> {

    @Override
    public List<RequestFieldChange> detectChanges(ProfessionalExperience currentProfessionalExperience, UpdateProfessionalExperienceRequest updateProfessionalExperienceRequest) {

        List<RequestFieldChange> changes = new ArrayList<>();

        addIfChanged(changes, "employmentType", currentProfessionalExperience.getEmploymentType(), updateProfessionalExperienceRequest.employmentType());
        addIfChanged(changes, "experienceLevel", currentProfessionalExperience.getExperienceLevel(), updateProfessionalExperienceRequest.experienceLevel());
        addIfChanged(changes, "companyName", currentProfessionalExperience.getCompanyName(), updateProfessionalExperienceRequest.companyName());
        addIfChanged(changes, "companyLocation", currentProfessionalExperience.getCompanyLocation(), updateProfessionalExperienceRequest.companyLocation());
        addIfChanged(changes, "jobTitle", currentProfessionalExperience.getJobTitle(), updateProfessionalExperienceRequest.jobTitle());
        addIfChanged(changes, "startDate", currentProfessionalExperience.getStartDate(), updateProfessionalExperienceRequest.startDate());
        addIfChanged(changes, "endDate", currentProfessionalExperience.getEndDate(), updateProfessionalExperienceRequest.endDate());
        addIfChanged(changes, "isCurrent", currentProfessionalExperience.getIsCurrent(), updateProfessionalExperienceRequest.isCurrent());
        addIfChanged(changes, "description", currentProfessionalExperience.getDescription(), updateProfessionalExperienceRequest.description());

        return changes;
    }

    // Helper method
    private void addIfChanged(List<RequestFieldChange> changes, String field, Object oldValue, Object newValue) {

        if (!Objects.equals(oldValue, newValue)) {

            changes.add(RequestFieldChange.builder()
                    .fieldName(field)
                    .oldValue(String.valueOf(oldValue))
                    .newValue(String.valueOf(newValue))
                    .build());
        }
    }
}
