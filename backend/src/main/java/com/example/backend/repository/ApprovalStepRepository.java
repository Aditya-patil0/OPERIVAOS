package com.example.backend.repository;

import com.example.backend.model.ApprovalStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalStepRepository extends JpaRepository<ApprovalStep, Long> {
    List<ApprovalStep> findByChainIdOrderByLevelNoAsc(Long chainId);
}
