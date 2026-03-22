package org.woridge.ms_internal_users_management.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.SuccessResponse;
import org.woridge.ms_internal_users_management.dto.promotiom.*;
import org.woridge.ms_internal_users_management.service.PromotionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/promotions")
@AllArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping
    public ResponseEntity<SuccessResponse<CreatePromotionResponse>> promoteUser(@Valid @RequestBody CreatePromotionRequest createPromotionRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Promotion created successfully",
                                promotionService.promoteUser(createPromotionRequest)
                        )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> updatePromotion(@PathVariable UUID id, @Valid @RequestBody UpdatePromotionRequest updatePromotionRequest) {

        promotionService.updatePromotion(id, updatePromotionRequest);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Promotion updated successfully",
                                null
                        )
                );
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<GetAllPromotionsResponse>>> getAllPromotions() {

        return ResponseEntity.ok(
                SuccessResponse.createSuccessResponse(
                        "Promotions retrieved successfully",
                        promotionService.getAllPromotions()
                )
        );
    }

    @GetMapping("/{CIN}")
    public ResponseEntity<SuccessResponse<UserPromotionInfoResponse>> getUserPromotionInfo(@PathVariable String CIN) {

        return ResponseEntity.ok(
                SuccessResponse.createSuccessResponse(
                        "User promotion information retrieved successfully",
                        promotionService.getUserPromotionInfo(CIN)
                )
        );
    }

    @GetMapping("/salary-history/{CIN}")
    public ResponseEntity<SuccessResponse<List<SalaryHistoryResponse>>> getSalaryHistory(@PathVariable String CIN) {

        return ResponseEntity.ok(
                SuccessResponse.createSuccessResponse(
                        "Salary history retrieved successfully",
                        promotionService.getSalaryHistory(CIN)
                )
        );
    }
}