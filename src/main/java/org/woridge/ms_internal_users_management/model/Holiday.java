package org.woridge.ms_internal_users_management.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.woridge.ms_internal_users_management.enumeration.HolidayStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="holidays")
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label_fr", nullable = false)
    private String labelFr;

    @Column(name = "label_ar", nullable = false)
    private String labelAr;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Integer duration;

    @Column(name = "is_fixed", nullable = false)
    private Boolean isFixed;

    @Enumerated(EnumType.STRING)
    private HolidayStatus status;
}
