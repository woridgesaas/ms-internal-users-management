package org.woridge.ms_internal_users_management.dto.payroll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayrollInput {
    private UUID userId;
    private String fullName;
    private String cin;
    private double baseSalary;
    private int familyDeductions;  //Nbrenfant/personnes

    // Test data
    private double workedDays;
    private String cnssNumber;
    private double bonus;          // Primes


    private double overtime25;  // Jour (semain)
    private double overtime50;  // Nuit (semain) OR Jour (Repos)
    private double overtime100; // Nuit (Repos/Holiday)
    public double overtimeHours(){
        return overtime25 + overtime50 + overtime100;
    }
}
