package org.woridge.ms_internal_users_management.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.SuccessResponse;
import org.woridge.ms_internal_users_management.dto.department.AddDepartmentRequest;
import org.woridge.ms_internal_users_management.dto.department.DepartmentResponse;
import org.woridge.ms_internal_users_management.dto.department.UpdateDepartmentRequest;
import org.woridge.ms_internal_users_management.service.DepartmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/departments")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<SuccessResponse<Void>> addDepartment(@Valid @RequestBody AddDepartmentRequest request) {
        departmentService.createDepartment(request);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAll() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody UpdateDepartmentRequest request) {

        departmentService.updateDepartment(id, request);
        return ResponseEntity.noContent().build();
    }
}