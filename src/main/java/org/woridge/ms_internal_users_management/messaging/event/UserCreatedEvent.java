package org.woridge.ms_internal_users_management.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserCreatedEvent {
    private String cin;
    private String firstName;
    private String lastName;
    private String email;
    private String departmentId;
    private String role;
}
