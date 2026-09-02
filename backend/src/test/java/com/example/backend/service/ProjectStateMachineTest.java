package com.example.backend.service;

import com.example.backend.model.ProjectState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectStateMachineTest {

    @Test
    void allowsInquiryToDiscussion() {
        assertTrue(ProjectStateMachine.isTransitionAllowed(
                ProjectState.INQUIRY, ProjectState.DISCUSSION));
    }

    @Test
    void rejectsInquiryToCompleted() {
        assertFalse(ProjectStateMachine.isTransitionAllowed(
                ProjectState.INQUIRY, ProjectState.COMPLETED));
    }

    @Test
    void rejectsReverseTransition() {
        assertFalse(ProjectStateMachine.isTransitionAllowed(
                ProjectState.DISCUSSION, ProjectState.INQUIRY));
    }

    @Test
    void rejectsSameStateTransition() {
        assertFalse(ProjectStateMachine.isTransitionAllowed(
                ProjectState.IN_PROGRESS, ProjectState.IN_PROGRESS));
    }

    @Test
    void allowsOnHoldToResumeAnyState() {
        assertTrue(ProjectStateMachine.isTransitionAllowed(
                ProjectState.ON_HOLD, ProjectState.APPROVED));
    }
}