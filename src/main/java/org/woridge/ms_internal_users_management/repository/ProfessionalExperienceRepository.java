package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.woridge.ms_internal_users_management.model.ProfessionalExperience;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ProfessionalExperienceRepository extends JpaRepository<ProfessionalExperience, UUID> {

    boolean existsByUserIdAndCompanyNameAndJobTitleAndStartDate(
            UUID userId,
            String companyName,
            String jobTitle,
            LocalDate startDate
    );

    boolean existsByUserIdAndCompanyNameAndJobTitleAndStartDateAndIdNot(
            UUID userId,
            String companyName,
            String jobTitle,
            LocalDate startDate,
            UUID id
    );
}
