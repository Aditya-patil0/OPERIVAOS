package com.example.backend.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "approval_steps")
public class ApprovalStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long chainId;

    @Column(nullable = false)
    private Integer levelNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role approverRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StepDecision decision;

    private Long actedBy;
    private Instant actedAt;

    public Long getId() {
        return id;
    }

    public Long getChainId() {
        return chainId;
    }

    public void setChainId(Long chainId) {
        this.chainId = chainId;
    }

    public Integer getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(Integer levelNo) {
        this.levelNo = levelNo;
    }

    public Role getApproverRole() {
        return approverRole;
    }

    public void setApproverRole(Role approverRole) {
        this.approverRole = approverRole;
    }

    public StepDecision getDecision() {
        return decision;
    }

    public void setDecision(StepDecision decision) {
        this.decision = decision;
    }

    public Long getActedBy() {
        return actedBy;
    }

    public void setActedBy(Long actedBy) {
        this.actedBy = actedBy;
    }

    public Instant getActedAt() {
        return actedAt;
    }

    public void setActedAt(Instant actedAt) {
        this.actedAt = actedAt;
    }
}