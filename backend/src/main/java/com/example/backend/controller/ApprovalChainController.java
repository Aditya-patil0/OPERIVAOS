package com.example.backend.controller;

import com.example.backend.dto.ApprovalChainCreateRequest;
import com.example.backend.dto.ApprovalDecisionRequest;
import com.example.backend.model.ApprovalChain;
import com.example.backend.model.ApprovalStep;
import com.example.backend.model.RequestType;
import com.example.backend.model.Role;
import com.example.backend.model.StepDecision;
import com.example.backend.repository.ProjectRoleRepository;
import com.example.backend.repository.ApprovalStepRepository;
import com.example.backend.service.ApprovalChainService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects/{projectId}/approvals")
public class ApprovalChainController {

    private final ApprovalChainService approvalChainService;
    private final ProjectRoleRepository projectRoleRepository;
    private final ApprovalStepRepository approvalStepRepository;

    public ApprovalChainController(ApprovalChainService approvalChainService,
                                   ProjectRoleRepository projectRoleRepository,
                                   ApprovalStepRepository approvalStepRepository) {
        this.approvalChainService = approvalChainService;
        this.projectRoleRepository = projectRoleRepository;
        this.approvalStepRepository = approvalStepRepository;
    }

    @PostMapping
    public ResponseEntity<?> startChain(@PathVariable Long projectId,
                                        @RequestBody ApprovalChainCreateRequest request,
                                        HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        ResponseEntity<?> accessError = checkProjectAccess(userId, projectId);
        if (accessError != null) {
            return accessError;
        }

        RequestType requestType = RequestType.valueOf(request.requestType());
        List<Role> approverRoles = request.approverRoles().stream()
                .map(Role::valueOf)
                .collect(Collectors.toList());

        ApprovalChain chain = approvalChainService.startChain(
                projectId, requestType, userId, approverRoles, request.slaHours());
        return ResponseEntity.status(HttpStatus.CREATED).body(chain);
    }

    @GetMapping("/{chainId}")
    public ResponseEntity<?> getChainWithSteps(@PathVariable Long projectId,
                                               @PathVariable Long chainId,
                                               HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        ResponseEntity<?> accessError = checkProjectAccess(userId, projectId);
        if (accessError != null) {
            return accessError;
        }

        return ResponseEntity.ok(approvalChainService.getChainWithSteps(chainId));
    }

    @PatchMapping("/{chainId}/steps/{levelNo}/decide")
    public ResponseEntity<?> decideStep(@PathVariable Long projectId,
                                        @PathVariable Long chainId,
                                        @PathVariable Integer levelNo,
                                        @RequestBody ApprovalDecisionRequest request,
                                        HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Authentication required"));
        }

        var projectRole = projectRoleRepository.findByUserIdAndProjectId(userId, projectId)
                .orElse(null);
        if (projectRole == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "You do not have access to this project"));
        }

        ApprovalStep step = approvalStepRepository.findByChainIdOrderByLevelNoAsc(chainId).stream()
                .filter(candidate -> candidate.getLevelNo().equals(levelNo))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Step not found"));
        if (projectRole.getRole() != step.getApproverRole()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Only a " + step.getApproverRole() + " can decide this step"));
        }

        try {
            StepDecision decision = StepDecision.valueOf(request.decision());
            ApprovalChain updatedChain = approvalChainService.decideStep(
                    chainId, levelNo, decision, userId);
            return ResponseEntity.ok(updatedChain);
        } catch (IllegalStateException exception) {
            return ResponseEntity.badRequest().body(Map.of("error", exception.getMessage()));
        }
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
