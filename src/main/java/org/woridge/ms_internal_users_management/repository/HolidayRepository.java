package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.woridge.ms_internal_users_management.model.Holiday;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepository extends CrudRepository<Holiday, Long> {
    boolean existsByDate(LocalDate date);
    boolean existsByDateAndIdNot(LocalDate date , Long id);

    @Query("SELECT h FROM Holiday h WHERE YEAR(h.date) = :year")
    List<Holiday> findAllByYear(@Param("year") int year);
}
