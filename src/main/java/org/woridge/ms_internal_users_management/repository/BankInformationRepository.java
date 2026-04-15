package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.woridge.ms_internal_users_management.model.BankInformation;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankInformationRepository extends JpaRepository<BankInformation, UUID> {

    Optional<BankInformation> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);

    boolean existsByRIB(String RIB);

    boolean existsByIBAN(String IBAN);

    boolean existsByRIBAndUserIdNot(String RIB, UUID userId);

    boolean existsByIBANAndUserIdNot(String IBAN, UUID userId);
}
