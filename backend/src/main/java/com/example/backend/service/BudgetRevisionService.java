package com.example.backend.service;

import com.example.backend.model.BudgetRevision;
import com.example.backend.repository.BudgetRevisionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetRevisionService {

    private final BudgetRevisionRepository budgetRevisionRepository;

    public BudgetRevisionService(BudgetRevisionRepository budgetRevisionRepository) {
        this.budgetRevisionRepository = budgetRevisionRepository;
    }

    public BudgetRevision addRevision(Long projectId, BigDecimal amount, String currency,
                                      String reason, Long requestedBy) {
        Optional<BudgetRevision> currentRevision =
                budgetRevisionRepository.findFirstByProjectIdOrderByVersionNoDesc(projectId);

        BudgetRevision revision = new BudgetRevision();
        revision.setProjectId(projectId);
        revision.setAmount(amount);
        revision.setCurrency(currency);
        revision.setReason(reason);
        revision.setRequestedBy(requestedBy);
        revision.setVersionNo(currentRevision.map(existing -> existing.getVersionNo() + 1).orElse(1));
        revision.setSupersedesId(currentRevision.map(BudgetRevision::getId).orElse(null));

        return budgetRevisionRepository.save(revision);
    }

    public Optional<BudgetRevision> getCurrentRevision(Long projectId) {
        return budgetRevisionRepository.findFirstByProjectIdOrderByVersionNoDesc(projectId);
    }

    public List<BudgetRevision> getFullHistory(Long projectId) {
        return budgetRevisionRepository.findByProjectIdOrderByVersionNoDesc(projectId);
    }
}
