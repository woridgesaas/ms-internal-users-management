package org.woridge.ms_internal_users_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "professional_experiences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessionalExperience {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;
    private String companyName;
    private String jobTitle;
    private String employmentType;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrent;
    private String description;
    private String logoPath;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
