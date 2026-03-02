package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.woridge.ms_internal_users_management.model.Post;
import org.woridge.ms_internal_users_management.model.Promotion;

import java.util.List;
import java.util.UUID;

public interface PromotionRepository extends JpaRepository<Promotion, UUID> {
}