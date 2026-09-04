package com.example.backend.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "meetings")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long projectId;

    @Column(nullable = false)
    private String purpose;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false)
    private Instant heldOn;

    @Column(nullable = false)
    private Long loggedBy;

    private String stageTag;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getHeldOn() {
        return heldOn;
    }

    public void setHeldOn(Instant heldOn) {
        this.heldOn = heldOn;
    }

    public Long getLoggedBy() {
        return loggedBy;
    }

    public void setLoggedBy(Long loggedBy) {
        this.loggedBy = loggedBy;
    }

    public String getStageTag() {
        return stageTag;
    }

    public void setStageTag(String stageTag) {
        this.stageTag = stageTag;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}