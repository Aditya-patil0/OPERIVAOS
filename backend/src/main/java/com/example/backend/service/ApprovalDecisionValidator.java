package com.example.backend.service;

import com.example.backend.model.ApprovalStep;
import com.example.backend.model.StepDecision;
import java.util.List;

public class ApprovalDecisionValidator {

    public static void validateCanDecide(List<ApprovalStep> orderedSteps, int levelNo) {
        for (ApprovalStep step : orderedSteps) {
            if (step.getLevelNo() < levelNo && step.getDecision() != StepDecision.APPROVED) {
                throw new IllegalStateException(
                        "Level " + step.getLevelNo() + " must be approved before level " + levelNo + " can be decided");
            }
        }
    }

    public static boolean isLastLevel(List<ApprovalStep> orderedSteps, int levelNo) {
        int maxLevel = orderedSteps.stream()
                .mapToInt(ApprovalStep::getLevelNo)
                .max()
                .orElse(0);
        return levelNo == maxLevel;
    }
}