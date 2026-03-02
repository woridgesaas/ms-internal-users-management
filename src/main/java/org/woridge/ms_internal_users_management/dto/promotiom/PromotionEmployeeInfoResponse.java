package org.woridge.ms_internal_users_management.dto.promotiom;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class PromotionEmployeeInfoResponse {

    private String fullName;
    private String departmentName;
    private String currentPost;
    private BigDecimal currentSalary;
}