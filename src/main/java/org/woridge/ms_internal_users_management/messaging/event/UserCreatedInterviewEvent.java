package org.woridge.ms_internal_users_management.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedInterviewEvent {
    private String userId;
    private String fullName;
    private String email;
    private String departmentId;
    private String cin;
    private String role;  // "RH", "Manager", "CEO", "Employee"
}