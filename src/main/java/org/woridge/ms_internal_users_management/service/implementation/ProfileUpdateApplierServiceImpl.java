package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.bank_information.UpdateBankInformationRequest;
import org.woridge.ms_internal_users_management.dto.education.UpdateEducationRequest;
import org.woridge.ms_internal_users_management.dto.professional_experience.UpdateProfessionalExperienceRequest;
import org.woridge.ms_internal_users_management.dto.user.UpdateUserPersonalInformationRequest;
import org.woridge.ms_internal_users_management.enumeration.BankName;
import org.woridge.ms_internal_users_management.enumeration.EducationType;
import org.woridge.ms_internal_users_management.enumeration.EmploymentType;
import org.woridge.ms_internal_users_management.enumeration.ExperienceLevel;
import org.woridge.ms_internal_users_management.exception.bank_information.BankInformationNotFoundException;
import org.woridge.ms_internal_users_management.exception.education.EducationNotFoundException;
import org.woridge.ms_internal_users_management.exception.professional_experience.ProfessionalExperienceNotFoundException;
import org.woridge.ms_internal_users_management.exception.user.UserNotFoundException;
import org.woridge.ms_internal_users_management.model.*;
import org.woridge.ms_internal_users_management.repository.BankInformationRepository;
import org.woridge.ms_internal_users_management.repository.EducationRepository;
import org.woridge.ms_internal_users_management.repository.ProfessionalExperienceRepository;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfileUpdateApplierServiceImpl implements ProfileUpdateApplierService {

    private final BankInformationService bankInformationService;
    private final EducationService educationService;
    private final ProfessionalExperienceService professionalExperienceService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final BankInformationRepository bankInformationRepository;
    private final EducationRepository educationRepository;
    private final ProfessionalExperienceRepository professionalExperienceRepository;

    @Override
    public void applyChanges(ProfileUpdateRequest profileUpdateRequest) {

        Map<String, String> fields = toMap(profileUpdateRequest.getFieldChanges());
        UUID userId = profileUpdateRequest.getUserId();
        UUID targetId = profileUpdateRequest.getTargetEntityId();

        switch (profileUpdateRequest.getSectionType()) {

            case PERSONAL_INFORMATION -> {

                UpdateUserPersonalInformationRequest updatePersonalInformationRequest = buildUpdatePersonalInformationRequest(userId, fields);
                userService.updateUserPersonalInformation(userId, updatePersonalInformationRequest);
            }

            case BANK_INFORMATION -> {

                UpdateBankInformationRequest updateBankInformationRequest = buildUpdateBankInformationRequest(targetId, fields);
                bankInformationService.updateBankInformation(userId, targetId, updateBankInformationRequest);
            }

            case EDUCATION -> {

                UpdateEducationRequest updateEducationRequest = buildUpdateEducationRequest(targetId, fields);
                educationService.updateEducation(userId, targetId, updateEducationRequest);
            }

            case PROFESSIONAL_EXPERIENCE -> {

                UpdateProfessionalExperienceRequest updateProfessionalExperienceRequest = buildUpdateProfessionalExperienceRequest(targetId, fields);
                professionalExperienceService.updateProfessionalExperience(userId, targetId, updateProfessionalExperienceRequest);
            }
        }
    }

    // Helper method
    private Map<String, String> toMap(List<RequestFieldChange> requestFieldChangeList) {

        return requestFieldChangeList
                .stream()
                .collect(Collectors.toMap(
                        RequestFieldChange::getFieldName,
                        c -> c.getNewValue() == null ? "null" : c.getNewValue()
                ));
    }

    private UpdateUserPersonalInformationRequest buildUpdatePersonalInformationRequest(UUID userId, Map<String, String> fields) {

        User currentUser = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id : " + userId + " not found"));

        return UpdateUserPersonalInformationRequest
                .builder()
                .firstName(fields.containsKey("firstName") ? fields.get("firstName") : currentUser.getFirstName())
                .lastName(fields.containsKey("lastName") ? fields.get("lastName") : currentUser.getLastName())
                .cin(fields.containsKey("cin") ? fields.get("cin") : currentUser.getCIN())
                .email(fields.containsKey("email") ? fields.get("email") : currentUser.getEmail())
                .phone(fields.containsKey("phone") ? fields.get("phone") : currentUser.getPhoneNumber())
                .birthDate(fields.containsKey("birthDate") ? (!"null".equals(fields.get("birthDate")) ? LocalDate.parse(fields.get("birthDate")) : null) : currentUser.getBirthDate())
                .address(fields.containsKey("address") ? fields.get("address") : currentUser.getAddress())
                .profileImageUrl(fields.containsKey("profileImage") ? fields.get("profileImage") : currentUser.getPicturePath())
                .build();
    }

    private UpdateBankInformationRequest buildUpdateBankInformationRequest(UUID bankInformationId, Map<String, String> fields) {

        BankInformation currentBankInformation = bankInformationRepository.findById(bankInformationId).orElseThrow(() ->
                new BankInformationNotFoundException("Bank information with id : " + bankInformationId + " not found"));

        return UpdateBankInformationRequest
                .builder()
                .id(bankInformationId)
                .name(fields.containsKey("name") ? BankName.valueOf(fields.get("name")) : currentBankInformation.getName())
                .accountHolderName(fields.containsKey("accountHolderName") ? fields.get("accountHolderName") : currentBankInformation.getAccountHolderName())
                .RIB(fields.containsKey("RIB") ? fields.get("RIB") : currentBankInformation.getRIB())
                .IBAN(fields.containsKey("IBAN") ? fields.get("IBAN") : currentBankInformation.getIBAN())
                .agencyName(fields.containsKey("agencyName") ? fields.get("agencyName") : currentBankInformation.getAgencyName())
                .agencyCity(fields.containsKey("agencyCity") ? fields.get("agencyCity") : currentBankInformation.getAgencyCity())
                .build();
    }

    private UpdateEducationRequest buildUpdateEducationRequest(UUID educationId, Map<String, String> fields) {

        Education currentEducation = educationRepository.findById(educationId).orElseThrow(() ->
                new EducationNotFoundException("Education with id : " + educationId + " not found "));

        return UpdateEducationRequest.builder()
                .id(educationId)
                .type(fields.containsKey("type") ? EducationType.valueOf(fields.get("type")) : currentEducation.getType())
                .title(fields.containsKey("title") ? fields.get("title") : currentEducation.getTitle())
                .startDate(fields.containsKey("startDate") ? LocalDate.parse(fields.get("startDate")) : currentEducation.getStartDate())
                .endDate(fields.containsKey("endDate") ? (!"null".equals(fields.get("endDate")) ? LocalDate.parse(fields.get("endDate")) : null) : currentEducation.getEndDate())
                .description(fields.containsKey("description") ? fields.get("description") : currentEducation.getDescription())
                .fieldOfStudy(fields.containsKey("fieldOfStudy") ? fields.get("fieldOfStudy") : currentEducation.getFieldOfStudy())
                .institution(fields.containsKey("institution") ? fields.get("institution") : currentEducation.getInstitution())
                .build();
    }

    private UpdateProfessionalExperienceRequest buildUpdateProfessionalExperienceRequest(UUID professionalExperienceId, Map<String, String> fields) {

        ProfessionalExperience currentExperience = professionalExperienceRepository.findById(professionalExperienceId).orElseThrow(() ->
                new ProfessionalExperienceNotFoundException("Professional experience with id : " + professionalExperienceId + " not found"));

        return UpdateProfessionalExperienceRequest
                .builder()
                .id(professionalExperienceId)
                .employmentType(fields.containsKey("employmentType") ? EmploymentType.valueOf(fields.get("employmentType")) : currentExperience.getEmploymentType())
                .experienceLevel(fields.containsKey("experienceLevel") ? ExperienceLevel.valueOf(fields.get("experienceLevel")) : currentExperience.getExperienceLevel())
                .companyName(fields.containsKey("companyName") ? fields.get("companyName") : currentExperience.getCompanyName())
                .companyLocation(fields.containsKey("companyLocation") ? fields.get("companyLocation") : currentExperience.getCompanyLocation())
                .jobTitle(fields.containsKey("jobTitle") ? fields.get("jobTitle") : currentExperience.getJobTitle())
                .startDate(fields.containsKey("startDate") ? LocalDate.parse(fields.get("startDate")) : currentExperience.getStartDate())
                .endDate(fields.containsKey("endDate") ? (!"null".equals(fields.get("endDate")) ? LocalDate.parse(fields.get("endDate")) : null) : currentExperience.getEndDate())
                .isCurrent(fields.containsKey("isCurrent") ? Boolean.parseBoolean(fields.get("isCurrent")) : currentExperience.getIsCurrent())
                .description(fields.containsKey("description") ? fields.get("description") : currentExperience.getDescription())
                .build();
    }
}
