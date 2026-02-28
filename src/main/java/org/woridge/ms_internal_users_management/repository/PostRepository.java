package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.woridge.ms_internal_users_management.model.Post;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
   Optional<Post>  findByNameIgnoreCase(String name);
}
