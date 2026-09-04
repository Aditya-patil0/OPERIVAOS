package com.example.backend.repository;

import com.example.backend.model.BudgetRevision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BudgetRevisionRepository extends JpaRepository<BudgetRevision, Long> {
    List<BudgetRevision> findByProjectIdOrderByVersionNoDesc(Long projectId);

    Optional<BudgetRevision> findFirstByProjectIdOrderByVersionNoDesc(Long projectId);
}
