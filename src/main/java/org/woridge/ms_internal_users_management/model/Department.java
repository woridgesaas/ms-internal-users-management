package org.woridge.ms_internal_users_management.model;

import jakarta.persistence.*;
import lombok.*;
import org.woridge.ms_internal_users_management.enumeration.Status;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)//utilisée pour enregistrer un Enum dans la base de données.
    @Column(nullable = false)
    private Status status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist // méthode appelée automatiquement avant INSERT dans la base.
    public void prePersist() { createdAt = Instant.now();}

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}