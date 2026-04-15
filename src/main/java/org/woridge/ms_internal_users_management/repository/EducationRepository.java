package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.woridge.ms_internal_users_management.enumeration.EducationType;
import org.woridge.ms_internal_users_management.model.Education;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface EducationRepository extends JpaRepository<Education, UUID> {

    boolean existsByUserIdAndTypeAndInstitutionAndTitleAndStartDate(
            UUID userId,
            EducationType type,
            String institution,
            String title,
            LocalDate startDate
    );

    boolean existsByUserIdAndTypeAndInstitutionAndTitleAndStartDateAndIdNot(
            UUID userId,
            EducationType type,
            String institution,
            String title,
            LocalDate startDate,
            UUID id
    );
}
