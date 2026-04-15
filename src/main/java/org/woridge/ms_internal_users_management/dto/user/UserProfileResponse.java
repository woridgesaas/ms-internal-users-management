package org.woridge.ms_internal_users_management.dto.user;

import lombok.Builder;
import org.woridge.ms_internal_users_management.dto.bank_information.BankInformationSummary;
import org.woridge.ms_internal_users_management.dto.education.EducationSummary;
import org.woridge.ms_internal_users_management.dto.professional_experience.ProfessionalExperienceSummary;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record UserProfileResponse(
        UUID id,
        String firstName,
        String lastName,
        String CIN,
        String email,
        String phoneNumber,
        String address,
        String picturePath,
        String role,
        String departmentName,
        String postName,
        Instant createdAt,
        LocalDate birthDate,
        BankInformationSummary bankInformationSummary,
        List<EducationSummary> educationSummaries,
        List<ProfessionalExperienceSummary> experienceSummaries
) {
}
