package org.woridge.ms_internal_users_management.dto.bank_information;

public record BankInformationDTO(
        String bankName,
        String accountNumber,
        String iban,
        String agency
) {}
