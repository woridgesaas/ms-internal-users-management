package org.woridge.ms_internal_users_management.dto.Holiday;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.woridge.ms_internal_users_management.enumeration.HolidayStatus;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayResponse {
    private Long id;
    private String labelFr;
    private String labelAr;
    private LocalDate date;
    private Integer duration;
    private Boolean isFixed;
    private HolidayStatus status;
}
