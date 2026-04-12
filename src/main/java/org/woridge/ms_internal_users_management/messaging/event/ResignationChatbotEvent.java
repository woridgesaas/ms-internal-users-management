package org.woridge.ms_internal_users_management
        .messaging.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResignationChatbotEvent {
    private String userId;
    private LocalDate dateDemission;
    private String tenant;
}