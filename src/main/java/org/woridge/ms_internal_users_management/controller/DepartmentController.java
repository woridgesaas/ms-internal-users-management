package org.woridge.ms_internal_users_management.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.department.CreateDepartmentRequest;
import org.woridge.ms_internal_users_management.dto.department.DepartmentResponse;
import org.woridge.ms_internal_users_management.dto.department.UpdateDepartmentRequest;
import org.woridge.ms_internal_users_management.service.DepartmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/departments")
@AllArgsConstructor
@PreAuthorize("hasRole('HR')")
public class DepartmentController {

    private final DepartmentService service;

    @PostMapping
    //@RequestBody(Spring convertit le JSON envoyé par le client en objet Java.)
    //@valid(ctive la validation automatique du DTO.)
    public ResponseEntity<Void> create(@Valid @RequestBody CreateDepartmentRequest request) {
        service.createDepartment(request);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAll() {
        return ResponseEntity.ok(service.getAllDepartments());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody UpdateDepartmentRequest request) {

        service.updateDepartment(id, request);
        return ResponseEntity.noContent().build();
    }


}