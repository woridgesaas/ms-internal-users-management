package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.woridge.ms_internal_users_management.model.PayrollDetail;

import java.util.UUID;

@Repository
public interface PayrollDetailsRepository extends CrudRepository<PayrollDetail, UUID> {
}
