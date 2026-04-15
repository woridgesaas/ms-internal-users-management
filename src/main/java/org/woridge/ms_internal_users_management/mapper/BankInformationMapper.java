package org.woridge.ms_internal_users_management.mapper;

import org.woridge.ms_internal_users_management.dto.bank_information.AddBankInformationRequest;
import org.woridge.ms_internal_users_management.dto.bank_information.AddBankInformationResponse;
import org.woridge.ms_internal_users_management.dto.bank_information.BankInformationSummary;
import org.woridge.ms_internal_users_management.dto.bank_information.UpdateBankInformationRequest;
import org.woridge.ms_internal_users_management.model.BankInformation;
import org.woridge.ms_internal_users_management.model.User;

public class BankInformationMapper {

    public static BankInformation toBankInformation(AddBankInformationRequest addBankInformationRequest, User user) {

        return BankInformation
                .builder()
                .name(addBankInformationRequest.name())
                .accountHolderName(addBankInformationRequest.accountHolderName())
                .RIB(addBankInformationRequest.RIB())
                .IBAN(addBankInformationRequest.IBAN())
                .agencyName(addBankInformationRequest.agencyName())
                .agencyCity(addBankInformationRequest.agencyCity())
                .user(user)
                .build();
    }

    public static AddBankInformationResponse toAddBankInformationResponse(BankInformation bankInformation) {

        return AddBankInformationResponse
                .builder()
                .id(bankInformation.getId())
                .name(bankInformation.getName())
                .accountHolderName(bankInformation.getAccountHolderName())
                .RIB(bankInformation.getRIB())
                .IBAN(bankInformation.getIBAN())
                .agencyName(bankInformation.getAgencyName())
                .agencyCity(bankInformation.getAgencyCity())
                .build();
    }

    public static void updateBankInformation(BankInformation currentBankInformation, UpdateBankInformationRequest updateBankInformationRequest) {

        currentBankInformation.setName(updateBankInformationRequest.name());
        currentBankInformation.setAccountHolderName(updateBankInformationRequest.accountHolderName());
        currentBankInformation.setRIB(updateBankInformationRequest.RIB());
        currentBankInformation.setIBAN(updateBankInformationRequest.IBAN());
        currentBankInformation.setAgencyName(updateBankInformationRequest.agencyName());
        currentBankInformation.setAgencyCity(updateBankInformationRequest.agencyCity());
    }

    public static BankInformationSummary toBankInformationSummary(BankInformation bankInformation) {

        return BankInformationSummary
                .builder()
                .id(bankInformation.getId())
                .name(bankInformation.getName())
                .accountHolderName(bankInformation.getAccountHolderName())
                .RIB(bankInformation.getRIB())
                .IBAN(bankInformation.getIBAN())
                .agencyName(bankInformation.getAgencyName())
                .agencyCity(bankInformation.getAgencyCity())
                .build();
    }
}
