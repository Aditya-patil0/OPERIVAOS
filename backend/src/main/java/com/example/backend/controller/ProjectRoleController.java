package com.example.backend.controller;

import com.example.backend.model.ProjectRole;
import com.example.backend.model.Role;
import com.example.backend.repository.ProjectRoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/projects")
public class ProjectRoleController {

    private final ProjectRoleRepository projectRoleRepository;

    public ProjectRoleController(ProjectRoleRepository projectRoleRepository) {
        this.projectRoleRepository = projectRoleRepository;
    }

    @PostMapping("/{projectId}/roles")
    public ResponseEntity<?> addRole(@PathVariable Long projectId,
                                    @RequestBody AddRoleRequest request,
                                    HttpServletRequest httpRequest) {
        Long callerUserId = (Long) httpRequest.getAttribute("userId");

        if (callerUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Authentication required"));
        }

        ProjectRole callerRole = projectRoleRepository.findByUserIdAndProjectId(callerUserId, projectId)
                .orElse(null);

        if (callerRole == null || callerRole.getRole() != Role.CEO) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Only a CEO can assign roles on this project"));
        }

        Long targetUserId = request.userId();
        ProjectRole projectRole = projectRoleRepository.findByUserIdAndProjectId(targetUserId, projectId)
                .orElseGet(ProjectRole::new);

        projectRole.setUserId(targetUserId);
        projectRole.setProjectId(projectId);
        projectRole.setRole(Role.valueOf(request.role()));
        projectRole.setGrantedBy(callerUserId);

        ProjectRole savedRole = projectRoleRepository.save(projectRole);
        return ResponseEntity.ok(savedRole);
    }

    public record AddRoleRequest(Long userId, String role) {
    }
}
