package com.example.backend.controller;

import com.example.backend.model.ProjectRole;
import com.example.backend.model.Role;
import com.example.backend.repository.ProjectRoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectRoleController {

    private final ProjectRoleRepository projectRoleRepository;

    public ProjectRoleController(ProjectRoleRepository projectRoleRepository) {
        this.projectRoleRepository = projectRoleRepository;
    }

    @PostMapping("/{projectId}/roles")
    public ResponseEntity<ProjectRole> addRole(@PathVariable Long projectId, @RequestBody AddRoleRequest request) {
        ProjectRole projectRole = new ProjectRole();
        projectRole.setUserId(request.userId());
        projectRole.setProjectId(projectId);
        projectRole.setRole(Role.valueOf(request.role()));

        ProjectRole savedRole = projectRoleRepository.save(projectRole);
        return ResponseEntity.ok(savedRole);
    }

    public record AddRoleRequest(Long userId, String role) {
    }
}
