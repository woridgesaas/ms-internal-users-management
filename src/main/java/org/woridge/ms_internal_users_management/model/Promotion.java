package org.woridge.ms_internal_users_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "promotions")
@Getter
@Setter
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Relation avec User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "promotion_date", nullable = false)
    private LocalDate promotionDate;

    @Column(name = "previous_salary", nullable = false)
    private BigDecimal previousSalary;

    @Column(name = "new_salary", nullable = false)
    private BigDecimal newSalary;

    @Column(name = "previous_post", nullable = false)
    private String previousPost;

    @Column(name = "new_post", nullable = false)
    private String newPost;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    // 🔹 Gestion automatique des dates
    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}