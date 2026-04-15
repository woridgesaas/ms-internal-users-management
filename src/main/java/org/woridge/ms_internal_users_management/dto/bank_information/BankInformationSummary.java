package org.woridge.ms_internal_users_management.dto.bank_information;

import lombok.Builder;
import org.woridge.ms_internal_users_management.enumeration.BankName;

import java.util.UUID;

@Builder
public record BankInformationSummary(
        UUID id,
        BankName name,
        String accountHolderName,
        String RIB,
        String IBAN,
        String agencyName,
        String agencyCity
) {
}
