package org.woridge.ms_internal_users_management.model;

import jakarta.persistence.*;
import lombok.*;
import org.woridge.ms_internal_users_management.enumeration.RequestStatus;
import org.woridge.ms_internal_users_management.enumeration.SectionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "profile_update_requests",
        indexes = {
                @Index(name = "idx_profile_update_requests_user_id", columnList = "user_id"),
                @Index(name = "idx_profile_update_requests_status", columnList = "status"),
                @Index(name = "idx_profile_update_requests_section", columnList = "section_type"),
                @Index(name = "idx_profile_update_requests_requested", columnList = "requested_at")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProfileUpdateRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private SectionType sectionType;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private UUID targetEntityId; // NULL for PERSONAL_INFORMATION, required for all other section types
    private LocalDateTime requestedAt;
    private LocalDateTime processedAt;
    private UUID processedBy;
    private UUID userId;

    @OneToMany(
            mappedBy = "profileUpdateRequest",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RequestFieldChange> fieldChanges;

    @PrePersist
    public void prePersist() {

        this.requestedAt = LocalDateTime.now();
        this.status = RequestStatus.PENDING;
    }
}
