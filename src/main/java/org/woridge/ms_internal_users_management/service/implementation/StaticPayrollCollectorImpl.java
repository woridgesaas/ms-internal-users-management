package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.payroll.PayrollInput;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.PayrollCollectorService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StaticPayrollCollectorImpl implements PayrollCollectorService {
    private final UserRepository userRepository;
    public List<PayrollInput> getPayrollInputs() {
        List<PayrollInput> inputs = new ArrayList<>();
        // Profil 1 : Salaire modéré, célibataire (0 déduction)
        inputs.add(new PayrollInput(UUID.randomUUID(), "Alice Dupont", "CIN12345", 9500.00, 2, 26,  "CNSS001122", 800.00 , 8 , 10 ,0));
        // Profil 2 : Gros salaire, marié + 2 enfants (3 déductions)
        //inputs.add(new PayrollInput(UUID.randomUUID(), "Zakariyae El Amrani", "AB123456", 12000.00, 3, 26, 15, "CNSS998877", 1500.00));
        // Profil 3 : Salaire moyen, 1 déduction, a manqué des jours
        //inputs.add(new PayrollInput(UUID.randomUUID(), "Mohamed Benali", "CIN67890", 8000.00, 1, 15, 5, "CNSS334455", 500.00));


        return inputs;
    }
}
