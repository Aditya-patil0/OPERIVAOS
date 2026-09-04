package com.example.backend.controller;

import com.example.backend.dto.MeetingCreateRequest;
import com.example.backend.model.Meeting;
import com.example.backend.repository.ProjectRoleRepository;
import com.example.backend.service.MeetingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects/{projectId}/meetings")
public class MeetingController {

    private final MeetingService meetingService;
    private final ProjectRoleRepository projectRoleRepository;

    public MeetingController(MeetingService meetingService, ProjectRoleRepository projectRoleRepository) {
        this.meetingService = meetingService;
        this.projectRoleRepository = projectRoleRepository;
    }

    @PostMapping
    public ResponseEntity<?> logMeeting(@PathVariable Long projectId,
                                        @RequestBody MeetingCreateRequest request,
                                        HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        ResponseEntity<?> accessError = checkProjectAccess(userId, projectId);
        if (accessError != null) {
            return accessError;
        }

        Meeting meeting = meetingService.logMeeting(
                projectId,
                request.purpose(),
                request.notes(),
                request.heldOn(),
                userId,
                request.stageTag());

        return ResponseEntity.status(HttpStatus.CREATED).body(meeting);
    }

    @GetMapping
    public ResponseEntity<?> getMeetingsForProject(@PathVariable Long projectId,
                                                   HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        ResponseEntity<?> accessError = checkProjectAccess(userId, projectId);
        if (accessError != null) {
            return accessError;
        }

        List<Meeting> meetings = meetingService.getMeetingsForProject(projectId);
        return ResponseEntity.ok(meetings);
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
