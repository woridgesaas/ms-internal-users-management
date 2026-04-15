package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.bank_information.AddBankInformationRequest;
import org.woridge.ms_internal_users_management.dto.bank_information.UpdateBankInformationRequest;
import org.woridge.ms_internal_users_management.exception.bank_information.BankInformationDuplicatedException;
import org.woridge.ms_internal_users_management.exception.bank_information.IbanDuplicatedException;
import org.woridge.ms_internal_users_management.exception.bank_information.RibDuplicatedException;
import org.woridge.ms_internal_users_management.repository.BankInformationRepository;
import org.woridge.ms_internal_users_management.service.BankInformationValidator;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BankInformationValidatorImpl implements BankInformationValidator {

    private final BankInformationRepository bankInformationRepository;

    @Override
    public void validateBankInformationForCreation(UUID userId, AddBankInformationRequest addBankInformationRequest) {

        if (bankInformationRepository.existsByUserId(userId)) {

            throw new BankInformationDuplicatedException("Bank information already exists for user with id : " + userId);
        }

        if (bankInformationRepository.existsByRIB(addBankInformationRequest.RIB())) {

            throw new RibDuplicatedException("RIB already exists");
        }

        if (bankInformationRepository.existsByIBAN(addBankInformationRequest.IBAN())) {

            throw new IbanDuplicatedException("IBAN already exists");
        }
    }

    @Override
    public void validateBankInformationForUpdate(UUID userId, UpdateBankInformationRequest updateBankInformationRequest) {

        if (bankInformationRepository.existsByRIBAndUserIdNot(updateBankInformationRequest.RIB(), userId)) {

            throw new RibDuplicatedException("RIB already exists");
        }

        if (bankInformationRepository.existsByIBANAndUserIdNot(updateBankInformationRequest.IBAN(), userId)) {

            throw new IbanDuplicatedException("IBAN already exists");
        }
    }
}
