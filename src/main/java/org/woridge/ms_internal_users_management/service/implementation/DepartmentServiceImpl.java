package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woridge.ms_internal_users_management.mapper.DepartmentMapper;
import org.woridge.ms_internal_users_management.repository.DepartmentRepository;
import org.woridge.ms_internal_users_management.dto.department.CreateDepartmentRequest;
import org.woridge.ms_internal_users_management.dto.department.DepartmentResponse;
import org.woridge.ms_internal_users_management.dto.department.UpdateDepartmentRequest;
import org.woridge.ms_internal_users_management.exception.DepartmentAlreadyExistsException;
import org.woridge.ms_internal_users_management.exception.DepartmentNotFoundException;
import org.woridge.ms_internal_users_management.model.Department;
import org.woridge.ms_internal_users_management.enumeration.StatusDepartment;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.DepartmentService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public void createDepartment(CreateDepartmentRequest request) {

        if (repository.existsByNameIgnoreCase(request.getName())) {
            throw new DepartmentAlreadyExistsException("Department already exists");
        }

        Department department = mapper.toEntity(request);
        repository.save(department);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {

        return repository.findAll()
                .stream()
                .map(dep -> {
                    long count = userRepository.countByDepartmentId(dep.getId());
                    return mapper.toDto(dep, (int) count);
                })
                .toList();
    }



    @Override
    @Transactional
    public void updateDepartment(UUID id, UpdateDepartmentRequest request) {

        Department department = repository.findById(id)
                .orElseThrow(() ->
                        new DepartmentNotFoundException("Department with id " + id + " not found")
                );

        repository.findByNameIgnoreCase(request.getName())
                .filter(dep -> !dep.getId().equals(id))
                .ifPresent(dep -> {
                    throw new DepartmentAlreadyExistsException("Department already exists");
                });

        department.setName(request.getName());
        department.setStatus(request.getStatus());
    }
}