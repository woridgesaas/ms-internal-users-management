package org.woridge.ms_internal_users_management.service.implementation;

import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.education.UpdateEducationRequest;
import org.woridge.ms_internal_users_management.model.Education;
import org.woridge.ms_internal_users_management.model.RequestFieldChange;
import org.woridge.ms_internal_users_management.service.ProfileUpdateChangeDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EducationUpdateChangeDetectorImpl implements ProfileUpdateChangeDetector<Education, UpdateEducationRequest> {

    @Override
    public List<RequestFieldChange> detectChanges(Education currentEducation, UpdateEducationRequest updateEducationRequest) {

        List<RequestFieldChange> changes = new ArrayList<>();

        addIfChanged(changes, "type", currentEducation.getType(), updateEducationRequest.type());
        addIfChanged(changes, "title", currentEducation.getTitle(), updateEducationRequest.title());
        addIfChanged(changes, "startDate", currentEducation.getStartDate(), updateEducationRequest.startDate());
        addIfChanged(changes, "endDate", currentEducation.getEndDate(), updateEducationRequest.endDate());
        addIfChanged(changes, "description", currentEducation.getDescription(), updateEducationRequest.description());
        addIfChanged(changes, "fieldOfStudy", currentEducation.getFieldOfStudy(), updateEducationRequest.fieldOfStudy());
        addIfChanged(changes, "institution", currentEducation.getInstitution(), updateEducationRequest.institution());

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
