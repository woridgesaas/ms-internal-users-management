package org.woridge.ms_internal_users_management.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.resignation.ResignationCreateRequest;
import org.woridge.ms_internal_users_management.dto.resignation.ResignationResponse;
import org.woridge.ms_internal_users_management.dto.resignation.ResignationUpdateRequest;
import org.woridge.ms_internal_users_management.service.ResignationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/resignations")
@AllArgsConstructor
public class ResignationController {

    private final ResignationService resignationService;

    @PostMapping
    public ResponseEntity<ResignationResponse> create(
            @RequestBody @Valid ResignationCreateRequest request,
            @RequestHeader("X-Subdomain") String subdomain) {  //

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resignationService.create(request, subdomain));  // ← passer
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResignationResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(resignationService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ResignationResponse>> getAll() {
        return ResponseEntity.ok(resignationService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResignationResponse> update(
            @PathVariable UUID id,
            @RequestBody ResignationUpdateRequest request) {
        return ResponseEntity.ok(resignationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        resignationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}