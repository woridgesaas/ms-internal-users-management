package org.woridge.ms_internal_users_management.dto.user;

import org.woridge.ms_internal_users_management.dto.bank_information.BankInformationDTO;
import org.woridge.ms_internal_users_management.dto.education.EducationDTO;
import org.woridge.ms_internal_users_management.dto.professional_experience.ProfessionalExperienceDTO;

import java.util.List;
import java.util.UUID;

public record EmployeeProfileResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address,
        String picturePath,
        String role,
        String departmentName,
        String postName,
        BankInformationDTO bankInformation,
        List<EducationDTO> educations,
        List<ProfessionalExperienceDTO> experiences
) {}
