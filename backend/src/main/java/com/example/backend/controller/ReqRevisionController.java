package com.example.backend.controller;

import com.example.backend.dto.ReqRevisionCreateRequest;
import com.example.backend.model.ReqRevision;
import com.example.backend.repository.ProjectRoleRepository;
import com.example.backend.service.ReqRevisionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/projects/{projectId}/requirements")
public class ReqRevisionController {

    private final ReqRevisionService reqRevisionService;
    private final ProjectRoleRepository projectRoleRepository;

    public ReqRevisionController(ReqRevisionService reqRevisionService,
                                 ProjectRoleRepository projectRoleRepository) {
        this.reqRevisionService = reqRevisionService;
        this.projectRoleRepository = projectRoleRepository;
    }

    @PostMapping
    public ResponseEntity<?> addRevision(@PathVariable Long projectId,
                                         @RequestBody ReqRevisionCreateRequest request,
                                         HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        ResponseEntity<?> accessError = checkProjectAccess(userId, projectId);
        if (accessError != null) {
            return accessError;
        }

        ReqRevision revision = reqRevisionService.addRevision(projectId, request.description(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(revision);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentRevision(@PathVariable Long projectId,
                                                HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        ResponseEntity<?> accessError = checkProjectAccess(userId, projectId);
        if (accessError != null) {
            return accessError;
        }

        Optional<ReqRevision> revision = reqRevisionService.getCurrentRevision(projectId);
        return ResponseEntity.ok(revision);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getFullHistory(@PathVariable Long projectId,
                                            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        ResponseEntity<?> accessError = checkProjectAccess(userId, projectId);
        if (accessError != null) {
            return accessError;
        }

        List<ReqRevision> revisions = reqRevisionService.getFullHistory(projectId);
        return ResponseEntity.ok(revisions);
    }

    private ResponseEntity<?> checkProjectAccess(Long userId, Long projectId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Authentication required"));
        }

        if (projectRoleRepository.findByUserIdAndProjectId(userId, projectId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "You do not have access to this project"));
        }

        return null;
    }
}
