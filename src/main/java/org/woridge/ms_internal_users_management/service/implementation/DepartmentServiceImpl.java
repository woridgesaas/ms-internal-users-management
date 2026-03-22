package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woridge.ms_internal_users_management.mapper.DepartmentMapper;
import org.woridge.ms_internal_users_management.repository.DepartmentRepository;
import org.woridge.ms_internal_users_management.dto.department.AddDepartmentRequest;
import org.woridge.ms_internal_users_management.dto.department.DepartmentResponse;
import org.woridge.ms_internal_users_management.dto.department.UpdateDepartmentRequest;
import org.woridge.ms_internal_users_management.exception.department.DepartmentAlreadyExistsException;
import org.woridge.ms_internal_users_management.exception.department.DepartmentNotFoundException;
import org.woridge.ms_internal_users_management.model.Department;
import org.woridge.ms_internal_users_management.service.DepartmentService;
import org.woridge.ms_internal_users_management.service.EmployeeStatisticsService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;
    private final EmployeeStatisticsService employeeStatisticsService;

    @Override
    public void createDepartment(AddDepartmentRequest request) {

        if (repository.existsByNameIgnoreCase(request.getName())) {  //Vérifier si existe
            throw new DepartmentAlreadyExistsException("Department already exists");
        }

        //Conversion DTO → Entity
        Department department = mapper.toEntity(request);

        //Sauvegarde
        repository.save(department);
    }

    @Override
    @Transactional(readOnly = true)//méthode est exécutée dans une transaction mais en lecture seule.
    public List<DepartmentResponse> getAllDepartments() {

        List<Department> departments = repository.findAll();

        List<UUID> ids = departments.stream()
                .map(Department::getId)
                .toList();

        Map<UUID, Long> counts =
                employeeStatisticsService.countEmployeesByDepartments(ids);

        return departments.stream()
                .map(dep -> mapper.toDto(
                        dep,
                        counts.getOrDefault(dep.getId(), 0L)
                ))
                .toList();
    }

    @Override
    public void updateDepartment(UUID id, UpdateDepartmentRequest request) {

        Department department = repository.findById(id)//vérifier existence
                .orElseThrow(() ->
                        new DepartmentNotFoundException("Department with id " + id + " not found")
                );

        repository.findByNameIgnoreCase(request.getName())//vérifier duplication nom
                .filter(dep -> !dep.getId().equals(id))//Si un autre département a le même nom → erreur.
                .ifPresent(dep -> {
                    throw new DepartmentAlreadyExistsException("Department already exists");
                });

        //modification
        department.setName(request.getName());
        department.setStatus(request.getStatus());
    }
}