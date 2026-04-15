package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.bank_information.AddBankInformationRequest;
import org.woridge.ms_internal_users_management.dto.bank_information.UpdateBankInformationRequest;

import java.util.UUID;

public interface BankInformationValidator {

    void validateBankInformationForCreation(UUID userId, AddBankInformationRequest addBankInformationRequest);

    void validateBankInformationForUpdate(UUID userId, UpdateBankInformationRequest updateBankInformationRequest);
}
