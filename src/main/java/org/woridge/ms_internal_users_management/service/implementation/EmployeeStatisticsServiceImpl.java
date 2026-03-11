package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.EmployeeStatisticsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeStatisticsServiceImpl implements EmployeeStatisticsService {

    private final UserRepository userRepository;
    @Override
    public Map<UUID, Long> countEmployeesByDepartments(List<UUID> departmentIds) {

        List<Object[]> results = userRepository.countEmployeesByDepartments(departmentIds);

        Map<UUID, Long> counts = new HashMap<>();

        for (Object[] row : results) {
            counts.put((UUID) row[0], (Long) row[1]);
        }

        return counts;
    }

    @Override
    public long countEmployeesByDepartment(UUID departmentId) {
        return userRepository.countByDepartmentId(departmentId);
    }

}