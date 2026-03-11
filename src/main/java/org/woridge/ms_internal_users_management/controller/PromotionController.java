package org.woridge.ms_internal_users_management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.promotiom.*;
import org.woridge.ms_internal_users_management.service.PromotionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/promotions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR')")

public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping("/employee/{cin}")
    public ResponseEntity<PromotionEmployeeInfoResponse> getEmployeeInfo(
            @PathVariable String cin) {

        return ResponseEntity.ok(
                promotionService.getEmployeeInfoByCin(cin)
        );
    }

    @PostMapping
    public ResponseEntity<PromotionResponse> promote(
            @Valid @RequestBody CreatePromotionRequest request) {

        return ResponseEntity.ok(
                promotionService.promoteUser(request)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotionResponse> updatePromotion(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePromotionRequest request) {

        return ResponseEntity.ok(
                promotionService.updatePromotion(id, request)
        );
    }

    @GetMapping
    public ResponseEntity<List<PromotionResponse>> getAllPromotions() {

        return ResponseEntity.ok(
                promotionService.getAllPromotions()
        );
    }

    @GetMapping("/salary-history/{cin}")
    public ResponseEntity<List<SalaryHistoryResponse>> getSalaryHistory(
            @PathVariable String cin) {

        return ResponseEntity.ok(
                promotionService.getSalaryHistory(cin)
        );
    }

}