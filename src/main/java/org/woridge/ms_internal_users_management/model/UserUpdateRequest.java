package org.woridge.ms_internal_users_management.model;

import jakarta.persistence.*;
import lombok.*;
import org.woridge.ms_internal_users_management.enumeration.RequestStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Entity
@Table(
        name = "user_update_requests",
        indexes = {
                @Index(name = "idx_request_user", columnList = "user_id"),
                @Index(name = "idx_request_status", columnList = "status")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID userId;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    private LocalDateTime requestedAt;
    private LocalDateTime processedAt;
    private UUID processedBy;
    @OneToMany(
            mappedBy = "request",
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