package com.example.backend.service;

import com.example.backend.dto.ProjectCreateRequest;
import com.example.backend.model.Project;
import com.example.backend.model.ProjectState;
import com.example.backend.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(ProjectCreateRequest request) {
        Project project = new Project();
        project.setTitle(request.title());
        project.setClientName(request.clientName());
        project.setState(ProjectState.INQUIRY);
        project.setCode(generateProjectCode());

        return projectRepository.save(project);
    }

    public Project findProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
    }

    public Project changeState(Long projectId, ProjectState newState) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        ProjectState currentState = project.getState();
        if (!ProjectStateMachine.isTransitionAllowed(currentState, newState)) {
            throw new IllegalStateException("Cannot transition from " + currentState + " to " + newState);
        }

        project.setState(newState);
        return projectRepository.save(project);
    }

    public List<Project> listProjects() {
        return projectRepository.findAll();
    }

    private String generateProjectCode() {
        int randomNumber = ThreadLocalRandom.current().nextInt(1_000_000);
        return String.format("PRJ-%06d", randomNumber);
    }
}
