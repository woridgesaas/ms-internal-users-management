package org.woridge.ms_internal_users_management.mapper;

import org.springframework.stereotype.Component;
import org.woridge.ms_internal_users_management.dto.bank_information.BankInformationDTO;
import org.woridge.ms_internal_users_management.dto.education.EducationDTO;
import org.woridge.ms_internal_users_management.dto.professional_experience.ProfessionalExperienceDTO;
import org.woridge.ms_internal_users_management.dto.user.EmployeeProfileResponse;
import org.woridge.ms_internal_users_management.model.BankInformation;
import org.woridge.ms_internal_users_management.model.Education;
import org.woridge.ms_internal_users_management.model.ProfessionalExperience;
import org.woridge.ms_internal_users_management.model.User;
import java.util.List;
import java.util.Set;

@Component
public class EmployeeProfileMapper {

    public EmployeeProfileResponse toResponse(User user) {

        return new EmployeeProfileResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getPicturePath(),
                user.getRole().getRoleName(),
                user.getDepartment().getName(),
                user.getPost().getName(),
                mapBank(user.getBankInformation()),
                mapEducations(user.getEducations()),
                mapExperiences(user.getProfessionalExperiences())
        );
    }

    private BankInformationDTO mapBank(BankInformation bank) {
        return new BankInformationDTO(
                bank.getName(),
                bank.getAccountNumber(),
                bank.getCodeIban(),
                bank.getAgency()
        );
    }

    private List<EducationDTO> mapEducations(Set<Education> educations) {
        return educations.stream()
                .map(e -> new EducationDTO(
                        e.getUniversity(),
                        e.getFieldOfStudy(),
                        e.getDegree(),
                        e.getStartFinalYear()
                ))
                .toList();
    }

    private List<ProfessionalExperienceDTO> mapExperiences(Set<ProfessionalExperience> experiences) {
        return experiences.stream()
                .map(exp -> new ProfessionalExperienceDTO(
                        exp.getCompanyName(),
                        exp.getJobTitle(),
                        exp.getEmploymentType(),
                        exp.getLocation(),
                        exp.getStartDate(),
                        exp.getEndDate(),
                        exp.getIsCurrent(),
                        exp.getDescription(),
                        exp.getLogoPath()
                ))
                .toList();
    }
}