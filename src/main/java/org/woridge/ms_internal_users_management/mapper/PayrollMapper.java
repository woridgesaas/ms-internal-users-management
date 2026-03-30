package org.woridge.ms_internal_users_management.mapper;

import org.springframework.stereotype.Component;
import org.woridge.ms_internal_users_management.dto.payroll.PayrollDetailResponseDTO;
import org.woridge.ms_internal_users_management.dto.payroll.PayrollLineDTO;
import org.woridge.ms_internal_users_management.dto.payroll.PayrollSummaryDTO;
import org.woridge.ms_internal_users_management.model.Payroll;
import org.woridge.ms_internal_users_management.model.PayrollDetail;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.repository.UserRepository;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PayrollMapper {

    private final UserRepository userRepository;

    // Payroll → PayrollSummaryDTO
    public PayrollSummaryDTO toSummaryDTO(Payroll payroll) {
        String fullName = "Employé inconnu";
        String cin = "";

        try {
            Optional<User> userOpt = userRepository
                    .findById(payroll.getUser().getId());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                fullName = user.getFirstName() + " " + user.getLastName();
                cin = user.getCIN();
            }
        } catch (Exception e) {
            // User introuvable → on laisse les valeurs par défaut
        }

        return PayrollSummaryDTO.builder()
                .id(payroll.getId())
                .periodDate(payroll.getPeriodDate())
                .month(payroll.getPeriodDate().getMonthValue())
                .year(payroll.getPeriodDate().getYear())
                .grossSalary(payroll.getGrossSalary())
                .netSalary(payroll.getNetSalary())
                .status(payroll.getStatus())
                .employeeFullName(fullName)
                .employeeCIN(cin)
                .build();
    }

    //  Payroll → PayrollDetailResponseDTO
    public PayrollDetailResponseDTO toDetailDTO(Payroll payroll) {
        List<PayrollLineDTO> lines = payroll.getDetails().stream()
                .map(this::toLineDTO)
                .toList();

        return PayrollDetailResponseDTO.builder()
                .id(payroll.getId())
                .periodDate(payroll.getPeriodDate())
                .baseSalary(payroll.getBaseSalaryAtDate())
                .grossSalary(payroll.getGrossSalary())
                .netSalary(payroll.getNetSalary())
                .totalOvertimeHours(payroll.getTotalOvertimeHours())
                .totalAbsenceDays(payroll.getTotalAbsenceDays())
                .status(payroll.getStatus())
                .generatedAt(payroll.getGeneratedAt())
                .details(lines)
                .build();
    }

    //  PayrollDetail → PayrollLineDTO
    public PayrollLineDTO toLineDTO(PayrollDetail detail) {
        return PayrollLineDTO.builder()
                .label(detail.getLabel())
                .type(detail.getType())
                .baseValue(detail.getBaseValue())
                .rate(detail.getRate())
                .amount(detail.getAmount())
                .build();
    }
}