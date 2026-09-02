package com.example.backend.controller;

import com.example.backend.dto.ProjectCreateRequest;
import com.example.backend.model.Project;
import com.example.backend.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
