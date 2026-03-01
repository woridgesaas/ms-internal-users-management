package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.department.CreateDepartmentRequest;
import org.woridge.ms_internal_users_management.dto.department.DepartmentResponse;
import org.woridge.ms_internal_users_management.dto.department.UpdateDepartmentRequest;
import java.util.UUID;

import java.util.List;

public interface DepartmentService {

    void createDepartment(CreateDepartmentRequest request);

    List<DepartmentResponse> getAllDepartments();

    void updateDepartment(UUID id, UpdateDepartmentRequest request);
}
