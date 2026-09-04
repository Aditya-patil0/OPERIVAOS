package com.example.backend.repository;

import com.example.backend.model.ApprovalChain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalChainRepository extends JpaRepository<ApprovalChain, Long> {
    List<ApprovalChain> findByProjectId(Long projectId);
}
