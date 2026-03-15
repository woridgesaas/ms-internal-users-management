package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.woridge.ms_internal_users_management.model.Department;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    //Vérifier si un département existe/mpêche les doublons
    boolean existsByNameIgnoreCase(String name);

    // objet conteneur utilisé pour représenter la présence ou l'absence d'une valeur, évitant les NullPointerException
    Optional<Department> findByNameIgnoreCase(String name);


    Optional<Department> findById(UUID id);

}