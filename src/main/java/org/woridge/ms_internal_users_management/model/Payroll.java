package org.woridge.ms_internal_users_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.woridge.ms_internal_users_management.enumeration.PayrollStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "payrolls")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // FK vers users (id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "period_date", nullable = false)
    private LocalDate periodDate;

    @Column(name = "base_salary_at_date", nullable = false)
    private Double baseSalaryAtDate;

    @Column(name = "total_overtime_hours")
    @Builder.Default
    private Double totalOvertimeHours = 0.0;

    @Column(name = "total_absence_days")
    @Builder.Default
    private Double totalAbsenceDays = 0.0;

    @Column(name = "gross_salary", nullable = false)
    private Double grossSalary;

    @Column(name = "net_salary", nullable = false)
    private Double netSalary;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private PayrollStatus status = PayrollStatus.EN_ATTENTE;

    @Column(name = "generated_at")
    private Instant generatedAt;

    @OneToMany(mappedBy = "payroll", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PayrollDetail> details = new ArrayList<>();

    public void addDetail(PayrollDetail detail) {
        details.add(detail);
        detail.setPayroll(this);
    }
}
