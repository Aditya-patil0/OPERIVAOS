package com.example.backend.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher {

    private static final String PROJECT_STATE_CHANGED_TOPIC = "project.state.changed";
    private static final String APPROVAL_GRANTED_TOPIC = "approval.granted";
    private static final String APPROVAL_REJECTED_TOPIC = "approval.rejected";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishProjectStateChanged(Long projectId, String oldState, String newState) {
        String message = String.format(
                "{\"projectId\":%d,\"oldState\":\"%s\",\"newState\":\"%s\"}",
                projectId,
                escapeJson(oldState),
                escapeJson(newState));

        kafkaTemplate.send(PROJECT_STATE_CHANGED_TOPIC, message);
    }

    public void publishApprovalGranted(Long chainId, Integer levelNo, Long actedBy) {
        String message = String.format(
                "{\"chainId\":%d,\"levelNo\":%d,\"actedBy\":%d}",
                chainId,
                levelNo,
                actedBy);

        kafkaTemplate.send(APPROVAL_GRANTED_TOPIC, message);
    }

    public void publishApprovalRejected(Long chainId, Integer levelNo, Long actedBy) {
        String message = String.format(
                "{\"chainId\":%d,\"levelNo\":%d,\"actedBy\":%d}",
                chainId,
                levelNo,
                actedBy);

        kafkaTemplate.send(APPROVAL_REJECTED_TOPIC, message);
    }

    private String escapeJson(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
