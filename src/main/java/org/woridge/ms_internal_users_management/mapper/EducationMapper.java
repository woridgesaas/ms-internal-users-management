package org.woridge.ms_internal_users_management.mapper;

import org.woridge.ms_internal_users_management.dto.education.AddEducationRequest;
import org.woridge.ms_internal_users_management.dto.education.AddEducationResponse;
import org.woridge.ms_internal_users_management.dto.education.EducationSummary;
import org.woridge.ms_internal_users_management.dto.education.UpdateEducationRequest;
import org.woridge.ms_internal_users_management.model.Education;
import org.woridge.ms_internal_users_management.model.User;

import java.util.List;
import java.util.Set;

public class EducationMapper {

    public static Education toEducation(User user, AddEducationRequest addEducationRequest) {

        return Education
                .builder()
                .type(addEducationRequest.type())
                .title(addEducationRequest.title())
                .startDate(addEducationRequest.startDate())
                .endDate(addEducationRequest.endDate())
                .description(addEducationRequest.description())
                .fieldOfStudy(addEducationRequest.fieldOfStudy())
                .institution(addEducationRequest.institution())
                .user(user)
                .build();
    }

    public static AddEducationResponse toAddEducationResponse(Education education) {

        return AddEducationResponse
                .builder()
                .id(education.getId())
                .type(education.getType())
                .title(education.getTitle())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .description(education.getDescription())
                .fieldOfStudy(education.getFieldOfStudy())
                .institution(education.getInstitution())
                .build();
    }

    public static void updateEducation(Education currentEducation, UpdateEducationRequest updateEducationRequest) {

        currentEducation.setType(updateEducationRequest.type());
        currentEducation.setTitle(updateEducationRequest.title());
        currentEducation.setStartDate(updateEducationRequest.startDate());
        currentEducation.setEndDate(updateEducationRequest.endDate());
        currentEducation.setDescription(updateEducationRequest.description());
        currentEducation.setFieldOfStudy(updateEducationRequest.fieldOfStudy());
        currentEducation.setInstitution(updateEducationRequest.institution());
    }

    public static List<EducationSummary> toEducationSummaries(Set<Education> educations) {

        return educations
                .stream()
                .map(EducationMapper::toEducationSummary)
                .toList();
    }

    public static EducationSummary toEducationSummary(Education education) {

        return EducationSummary
                .builder()
                .id(education.getId())
                .type(education.getType())
                .title(education.getTitle())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .description(education.getDescription())
                .fieldOfStudy(education.getFieldOfStudy())
                .institution(education.getInstitution())
                .build();
    }
}