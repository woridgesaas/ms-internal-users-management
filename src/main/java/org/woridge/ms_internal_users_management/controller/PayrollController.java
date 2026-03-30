package org.woridge.ms_internal_users_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.payroll.PayrollDetailResponseDTO;
import org.woridge.ms_internal_users_management.dto.payroll.PayrollSummaryDTO;
import org.woridge.ms_internal_users_management.enumeration.ExportFormat;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.PayrollExportService;
import org.woridge.ms_internal_users_management.service.PayrollService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payrolls")
@Controller
public class PayrollController {
    @Autowired
    private PayrollExportService payrollExportService;

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/download")
    public ResponseEntity<byte[]> testDownload(
            @RequestParam UUID userId,
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam(defaultValue = "PDF") ExportFormat format) {

        try {

            byte[] pdfContent = payrollExportService.exportPayroll(userId, month, year, format);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            // "inline" to open the file in browser instead of download it direct
            String fileName = "Fiche_Paie_" + month + "_" + year + ".pdf";
            headers.setContentDisposition(ContentDisposition.inline()
                    .filename(fileName)
                    .build());

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    // --- EMPLOYÉ ---

    // GET /api/internal-users/payrolls/mine
    // L'employé voit ses propres fiches
    @GetMapping("/mine")
    public ResponseEntity<List<PayrollSummaryDTO>> getMyPayrolls(
            @RequestHeader("X-User-CIN") String cin) {

        User user = userRepository.findByCIN(cin)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        return ResponseEntity.ok(payrollService.getMyPayrolls(user.getId()));
    }

    // GET /api/internal-users/payrolls/{id}/download-payroll
    // L'employé télécharge une fiche validée
    @GetMapping("/{id}/download-payroll")
    public ResponseEntity<byte[]> downloadMyPayroll(
            @PathVariable UUID id,
            @RequestHeader("X-User-CIN") String cin,
            @RequestParam(defaultValue = "PDF") ExportFormat format) {

        try {
            User user = userRepository.findByCIN(cin)
                    .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

            byte[] pdf = payrollService.downloadPayroll(id, user.getId(), format);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline()
                    .filename("fiche_paie.pdf")
                    .build());

            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // --- RH / ADMIN ---

    // GET /api/internal-users/payrolls/all?month=3&year=2026
    // RH voit toutes les fiches d'un mois
    @GetMapping("/all")
    public ResponseEntity<List<PayrollSummaryDTO>> getAllByMonth(
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(payrollService.getAllPayrollsByMonth(month, year));
    }

    // GET /api/internal-users/payrolls/{id}/detail
    // RH voit le détail complet d'une fiche
    @GetMapping("/{id}/detail")
    public ResponseEntity<PayrollDetailResponseDTO> getDetail(
            @PathVariable UUID id) {
        return ResponseEntity.ok(payrollService.getPayrollDetail(id));
    }

    // PUT /api/internal-users/payrolls/{id}/validate
    // RH valide une fiche → employé peut télécharger
    @PutMapping("/{id}/validate")
    public ResponseEntity<Void> validate(@PathVariable UUID id) {
        payrollService.validatePayroll(id);
        return ResponseEntity.ok().build();
    }

    // PUT /api/internal-users/payrolls/{id}/reject
    // RH rejette une fiche → à recalculer
    @PutMapping("/{id}/reject")
    public ResponseEntity<Void> reject(@PathVariable UUID id) {
        payrollService.rejectPayroll(id);
        return ResponseEntity.ok().build();
    }

    // POST /api/internal-users/payrolls/generate?month=3&year=2026
    // RH génère manuellement les fiches d'un mois
    @PostMapping("/generate")
    public ResponseEntity<Void> generate(
            @RequestParam int month,
            @RequestParam int year) {
        payrollService.generatePayrolls(month, year);
        return ResponseEntity.ok().build();
    }


}
