package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.woridge.ms_internal_users_management.model.Post;
import org.woridge.ms_internal_users_management.model.Promotion;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
   Optional<Post>  findByNameIgnoreCase(String name);

}
