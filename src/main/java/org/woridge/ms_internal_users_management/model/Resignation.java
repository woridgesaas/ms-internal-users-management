package org.woridge.ms_internal_users_management.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "resignations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resignation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Column(name = "notice_date", nullable = false)
    private LocalDate noticeDate;

    @Column(name = "resignation_date", nullable = false)
    private LocalDate resignationDate;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}