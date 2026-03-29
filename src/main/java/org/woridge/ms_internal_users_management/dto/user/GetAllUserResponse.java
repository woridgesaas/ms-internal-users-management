package org.woridge.ms_internal_users_management.dto.user;

import lombok.*;
import org.woridge.ms_internal_users_management.enumeration.Status;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetAllUserResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Status status;
    private String picturePath;
    private String postName;
    private Instant createdAt; // temporaire pour JoiningDate
}
