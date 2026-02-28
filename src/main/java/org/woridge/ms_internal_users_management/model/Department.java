package org.woridge.ms_internal_users_management.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.woridge.ms_internal_users_management.enumeration.Status;

import java.util.UUID;

@Entity
@Table(name = "departments")
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;
}

