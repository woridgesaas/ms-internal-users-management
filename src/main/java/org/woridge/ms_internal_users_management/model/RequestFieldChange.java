package org.woridge.ms_internal_users_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "request_field_changes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestFieldChange {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fieldName;
    private String oldValue;
    private String newValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private ProfileUpdateRequest profileUpdateRequest;
}