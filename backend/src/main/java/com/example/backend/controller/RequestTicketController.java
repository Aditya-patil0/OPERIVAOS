package com.example.backend.controller;

import com.example.backend.dto.RequestTicketCreateRequest;
import com.example.backend.model.RequestTicket;
import com.example.backend.model.RequestType;
import com.example.backend.model.Role;
import com.example.backend.repository.ProjectRoleRepository;
import com.example.backend.service.RequestTicketService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects/{projectId}/tickets")
public class RequestTicketController {

    private final RequestTicketService requestTicketService;
    private final ProjectRoleRepository projectRoleRepository;

    public RequestTicketController(RequestTicketService requestTicketService,
                                   ProjectRoleRepository projectRoleRepository) {
        this.requestTicketService = requestTicketService;
        this.projectRoleRepository = projectRoleRepository;
    }

    @PostMapping
    public ResponseEntity<?> raiseTicket(@PathVariable Long projectId,
                                         @RequestBody RequestTicketCreateRequest request,
                                         HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        ResponseEntity<?> accessError = checkProjectAccess(userId, projectId);
        if (accessError != null) {
            return accessError;
        }

        RequestType type = RequestType.valueOf(request.type());
        List<Role> approverRoles = request.approverRoles().stream()
                .map(Role::valueOf)
                .collect(Collectors.toList());

        RequestTicket ticket = requestTicketService.raiseTicket(
                projectId,
                type,
                userId,
                request.routedToDept(),
                request.details(),
                approverRoles,
                request.slaHours());
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<?> getTicketWithStatus(@PathVariable Long projectId,
                                                 @PathVariable Long ticketId,
                                                 HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        ResponseEntity<?> accessError = checkProjectAccess(userId, projectId);
        if (accessError != null) {
            return accessError;
        }

        return ResponseEntity.ok(requestTicketService.getTicketWithStatus(ticketId));
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
