package org.woridge.ms_internal_users_management.dto.Holiday;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.woridge.ms_internal_users_management.enumeration.HolidayStatus;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor

public class HolidayUpdateRequest {
    private Long id;
    @NotBlank(message = "French label is required")
    @Size(min = 3, max = 100, message = "French label must be between 3 and 100 characters")
    private String labelFr;

    @NotBlank(message = "Arabic label is required")
    @Size(min = 3, max = 100, message = "Arabic label must be between 3 and 100 characters")
    private String labelAr;

    @NotNull(message = "Date is required")
    @Future(message = "Holiday date must be in the future")
    private LocalDate date;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 day")
    @Max(value = 30, message = "Duration cannot exceed 30 days")
    private Integer duration;

    @NotNull(message = "Status is required")
    private HolidayStatus status;
}
