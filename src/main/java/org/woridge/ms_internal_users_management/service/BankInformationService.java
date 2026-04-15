package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.bank_information.AddBankInformationRequest;
import org.woridge.ms_internal_users_management.dto.bank_information.AddBankInformationResponse;
import org.woridge.ms_internal_users_management.dto.bank_information.UpdateBankInformationRequest;

import java.util.UUID;

public interface BankInformationService {

    AddBankInformationResponse AddBankInformation(UUID userId, AddBankInformationRequest addBankInformationRequest);

    void updateBankInformation(UUID userId,UUID bankInformationId, UpdateBankInformationRequest updateBankInformationRequest);
}
