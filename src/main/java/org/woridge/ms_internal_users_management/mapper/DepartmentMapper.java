package org.woridge.ms_internal_users_management.mapper;

import org.springframework.stereotype.Component;
import org.woridge.ms_internal_users_management.dto.department.CreateDepartmentRequest;
import org.woridge.ms_internal_users_management.dto.department.DepartmentResponse;
import org.woridge.ms_internal_users_management.enumeration.Status;
import org.woridge.ms_internal_users_management.model.Department;

@Component
public class DepartmentMapper {

    public DepartmentResponse toDto(Department dep, long count) {
        return DepartmentResponse.builder()
                .id(dep.getId())
                .name(dep.getName())
                .status(dep.getStatus())
                .noOfEmployees(count)
                .build();
    }
    public Department toEntity(CreateDepartmentRequest request) {
        return Department.builder()
                .name(request.getName().trim())
                .status(request.getStatus())
                .build();
    }
}