package com.example.backend.controller;

import com.example.backend.dto.ProjectCreateRequest;
import com.example.backend.model.Project;
import com.example.backend.model.ProjectState;
import com.example.backend.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@RequestBody ProjectCreateRequest request) {
        return projectService.createProject(request);
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.findProjectById(id);
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.listProjects();
    }

    @PatchMapping("/{id}/state")
    public Project updateProjectState(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String stateName = request.get("state");
        if (stateName == null || stateName.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "state is required");
        }

        try {
            ProjectState newState = ProjectState.valueOf(stateName.trim().toUpperCase());
            return projectService.changeState(id, newState);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid state: " + stateName);
        }
    }
}
