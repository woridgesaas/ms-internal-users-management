package org.woridge.ms_internal_users_management.service.implementation;

import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.bank_information.UpdateBankInformationRequest;
import org.woridge.ms_internal_users_management.model.BankInformation;
import org.woridge.ms_internal_users_management.model.RequestFieldChange;
import org.woridge.ms_internal_users_management.service.ProfileUpdateChangeDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BankInformationUpdateChangeDetectorImpl implements ProfileUpdateChangeDetector<BankInformation, UpdateBankInformationRequest> {

    @Override
    public List<RequestFieldChange> detectChanges(BankInformation currentBankInformation, UpdateBankInformationRequest updateBankInformationRequest) {

        List<RequestFieldChange> changes = new ArrayList<>();

        addIfChanged(changes, "name",              currentBankInformation.getName(),              updateBankInformationRequest.name());
        addIfChanged(changes, "accountHolderName", currentBankInformation.getAccountHolderName(), updateBankInformationRequest.accountHolderName());
        addIfChanged(changes, "RIB",               currentBankInformation.getRIB(),               updateBankInformationRequest.RIB());
        addIfChanged(changes, "IBAN",              currentBankInformation.getIBAN(),              updateBankInformationRequest.IBAN());
        addIfChanged(changes, "agencyName",        currentBankInformation.getAgencyName(),        updateBankInformationRequest.agencyName());
        addIfChanged(changes, "agencyCity",        currentBankInformation.getAgencyCity(),        updateBankInformationRequest.agencyCity());

        return changes;
    }

    // Helper method
    private void addIfChanged(List<RequestFieldChange> changes, String field, Object oldValue, Object newValue) {

        if (!Objects.equals(oldValue, newValue)) {

            changes.add(RequestFieldChange.builder()
                    .fieldName(field)
                    .oldValue(String.valueOf(oldValue))
                    .newValue(String.valueOf(newValue))
                    .build());
        }
    }
}
