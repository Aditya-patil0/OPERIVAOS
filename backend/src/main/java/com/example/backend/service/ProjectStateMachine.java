package com.example.backend.service;

import com.example.backend.model.ProjectState;
import java.util.Map;
import java.util.Set;

public class ProjectStateMachine {

    private static final Map<ProjectState, Set<ProjectState>> ALLOWED_TRANSITIONS = Map.of(
            ProjectState.INQUIRY, Set.of(ProjectState.DISCUSSION, ProjectState.ON_HOLD, ProjectState.REJECTED),
            ProjectState.DISCUSSION,
            Set.of(ProjectState.REQUIREMENTS_DEFINED, ProjectState.ON_HOLD, ProjectState.REJECTED),
            ProjectState.REQUIREMENTS_DEFINED, Set.of(ProjectState.BUDGET_PROPOSED, ProjectState.ON_HOLD),
            ProjectState.BUDGET_PROPOSED, Set.of(ProjectState.UNDER_APPROVAL, ProjectState.ON_HOLD),
            ProjectState.UNDER_APPROVAL, Set.of(ProjectState.APPROVED, ProjectState.REJECTED, ProjectState.ON_HOLD),
            ProjectState.APPROVED, Set.of(ProjectState.IN_PROGRESS, ProjectState.ON_HOLD),
            ProjectState.IN_PROGRESS, Set.of(ProjectState.REVIEW, ProjectState.ON_HOLD),
            ProjectState.REVIEW, Set.of(ProjectState.COMPLETED, ProjectState.IN_PROGRESS, ProjectState.ON_HOLD),
            ProjectState.ON_HOLD, Set.of(ProjectState.values()));

    public static boolean isTransitionAllowed(ProjectState from, ProjectState to) {
        if (from == to)
            return false;
        Set<ProjectState> allowed = ALLOWED_TRANSITIONS.get(from);
        return allowed != null && allowed.contains(to);
    }
}