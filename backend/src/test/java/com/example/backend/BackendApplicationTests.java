package com.example.backend;

import com.example.backend.model.Project;
import com.example.backend.model.ProjectState;
import com.example.backend.repository.ProjectRepository;
import com.example.backend.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BackendApplicationTests {

	@Mock
	private ProjectRepository projectRepository;

	@InjectMocks
	private ProjectService projectService;

	@Test
	void contextLoads() {
	}

	@Test
	void changeState_updatesProjectWhenTransitionIsAllowed() {
		Project project = new Project();
		ReflectionTestUtils.setField(project, "id", 1L);
		project.setState(ProjectState.INQUIRY);

		when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
		when(projectRepository.save(project)).thenReturn(project);

		Project updated = projectService.changeState(1L, ProjectState.DISCUSSION);

		assertEquals(ProjectState.DISCUSSION, updated.getState());
		verify(projectRepository).save(project);
	}

	@Test
	void changeState_throwsWhenTransitionIsNotAllowed() {
		Project project = new Project();
		ReflectionTestUtils.setField(project, "id", 1L);
		project.setState(ProjectState.APPROVED);

		when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

		IllegalStateException exception = assertThrows(IllegalStateException.class,
				() -> projectService.changeState(1L, ProjectState.REJECTED));

		assertEquals("Cannot transition from APPROVED to REJECTED", exception.getMessage());
		verify(projectRepository, never()).save(any());
	}

	@Test
	void changeState_throwsWhenProjectDoesNotExist() {
		when(projectRepository.findById(99L)).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> projectService.changeState(99L, ProjectState.DISCUSSION));

		assertEquals("Project not found", exception.getMessage());
		verify(projectRepository, never()).save(any());
	}
}
