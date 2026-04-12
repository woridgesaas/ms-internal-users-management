package org.woridge.ms_internal_users_management
        .messaging.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaireChatbotEvent {
    private String userId;
    private BigDecimal montant;
    private String mois;
    private String tenant;
}