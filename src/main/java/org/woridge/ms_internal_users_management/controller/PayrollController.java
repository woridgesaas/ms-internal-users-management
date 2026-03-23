package org.woridge.ms_internal_users_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.woridge.ms_internal_users_management.enumeration.ExportFormat;
import org.woridge.ms_internal_users_management.service.PayrollExportService;

import java.util.UUID;

@RestController
@RequestMapping("/payrolls")
@Controller
public class PayrollController {
    @Autowired
    private PayrollExportService payrollExportService;

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
}
