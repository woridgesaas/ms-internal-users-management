package org.woridge.ms_internal_users_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "request_field_changes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestFieldChange {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserUpdateRequest request;
    private String fieldName;
    private String oldValue;
    private String newValue;
}
