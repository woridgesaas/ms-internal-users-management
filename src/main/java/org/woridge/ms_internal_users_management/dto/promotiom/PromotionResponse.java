package org.woridge.ms_internal_users_management.dto.promotiom;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class PromotionResponse {

    private UUID id;

    private String fullName;
    private String department;
    private String previousPost;
    private String newPost;
    private BigDecimal previousSalary;
    private BigDecimal newSalary;
    private LocalDate promotionDate;
}