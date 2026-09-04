package com.example.backend.controller;

import com.example.backend.dto.ApprovalChainCreateRequest;
import com.example.backend.model.ApprovalChain;
import com.example.backend.model.RequestType;
import com.example.backend.model.Role;
import com.example.backend.repository.ProjectRoleRepository;
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

    public ApprovalChainController(ApprovalChainService approvalChainService,
                                   ProjectRoleRepository projectRoleRepository) {
        this.approvalChainService = approvalChainService;
        this.projectRoleRepository = projectRoleRepository;
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
