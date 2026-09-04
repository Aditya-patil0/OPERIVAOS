package com.example.backend.controller;

import com.example.backend.dto.BudgetRevisionCreateRequest;
import com.example.backend.model.BudgetRevision;
import com.example.backend.repository.ProjectRoleRepository;
import com.example.backend.service.BudgetRevisionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/projects/{projectId}/budget")
public class BudgetRevisionController {

    private final BudgetRevisionService budgetRevisionService;
    private final ProjectRoleRepository projectRoleRepository;

    public BudgetRevisionController(BudgetRevisionService budgetRevisionService,
                                    ProjectRoleRepository projectRoleRepository) {
        this.budgetRevisionService = budgetRevisionService;
        this.projectRoleRepository = projectRoleRepository;
    }

    @PostMapping
    public ResponseEntity<?> addRevision(@PathVariable Long projectId,
                                         @RequestBody BudgetRevisionCreateRequest request,
                                         HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        ResponseEntity<?> accessError = checkProjectAccess(userId, projectId);
        if (accessError != null) {
            return accessError;
        }

        BudgetRevision revision = budgetRevisionService.addRevision(
                projectId,
                request.amount(),
                request.currency(),
                request.reason(),
                userId);
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

        Optional<BudgetRevision> revision = budgetRevisionService.getCurrentRevision(projectId);
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

        List<BudgetRevision> revisions = budgetRevisionService.getFullHistory(projectId);
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
