package org.woridge.ms_internal_users_management.dto.bank_information;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.woridge.ms_internal_users_management.enumeration.BankName;

import java.util.UUID;

@Builder
public record UpdateBankInformationRequest(

        @NotNull(message = "Bank information id is required")
        UUID id,

        @NotNull(message = "Bank name is required")
        BankName name,

        @NotBlank(message = "Account holder name is required")
        @Size(min = 3, max = 50, message = "Account holder name must be between 3 and 50 characters")
        String accountHolderName,

        @NotBlank(message = "RIB is required")
        @Pattern(regexp = "\\d{24}", message = "RIB must contain exactly 24 digits")
        String RIB,

        @NotBlank(message = "IBAN is required")
        @Pattern(regexp = "^MA\\d{26}$", message = "IBAN must start with 'MA' and contain 28 characters")
        String IBAN,

        @NotBlank(message = "Agency name is required")
        @Size(min = 5, max = 50, message = "Agency name must be between 5 and 50 characters")
        String agencyName,

        @NotBlank(message = "Agency city is required")
        @Size(min = 3, max = 50, message = "Agency city must be between 3 and 50 characters")
        String agencyCity
) {
}
