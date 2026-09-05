package com.example.backend.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher {

    private static final String PROJECT_STATE_CHANGED_TOPIC = "project.state.changed";

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

    private String escapeJson(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
