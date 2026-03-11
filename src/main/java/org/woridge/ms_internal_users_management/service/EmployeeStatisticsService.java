package org.woridge.ms_internal_users_management.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface EmployeeStatisticsService {


    long countEmployeesByDepartment(UUID departmentId);//1 département
    Map<UUID, Long> countEmployeesByDepartments(List<UUID> departmentIds);

}
