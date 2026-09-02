package com.example.backend.repository;

import com.example.backend.model.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Long> {
    Optional<ProjectRole> findByUserIdAndProjectId(Long userId, Long projectId);
}
