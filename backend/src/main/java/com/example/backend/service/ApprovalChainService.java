package com.example.backend.service;

import com.example.backend.model.ApprovalChain;
import com.example.backend.model.ApprovalStep;
import com.example.backend.model.ChainStatus;
import com.example.backend.model.RequestType;
import com.example.backend.model.Role;
import com.example.backend.model.StepDecision;
import com.example.backend.repository.ApprovalChainRepository;
import com.example.backend.repository.ApprovalStepRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class ApprovalChainService {

    private final ApprovalChainRepository approvalChainRepository;
    private final ApprovalStepRepository approvalStepRepository;

    public ApprovalChainService(ApprovalChainRepository approvalChainRepository,
                                ApprovalStepRepository approvalStepRepository) {
        this.approvalChainRepository = approvalChainRepository;
        this.approvalStepRepository = approvalStepRepository;
    }

    public ApprovalChain startChain(Long projectId, RequestType requestType, Long createdBy,
                                    List<Role> approverRolesInOrder, Integer slaHours) {
        ApprovalChain chain = new ApprovalChain();
        chain.setProjectId(projectId);
        chain.setRequestType(requestType);
        chain.setCreatedBy(createdBy);
        chain.setSlaHours(slaHours);
        chain.setStatus(ChainStatus.PENDING);

        ApprovalChain savedChain = approvalChainRepository.save(chain);

        List<ApprovalStep> steps = IntStream.range(0, approverRolesInOrder.size())
            .mapToObj(index -> createStep(savedChain.getId(),
                approverRolesInOrder.get(index), index + 1))
                .toList();
        approvalStepRepository.saveAll(steps);

        return savedChain;
    }

    public Map<String, Object> getChainWithSteps(Long chainId) {
        ApprovalChain chain = approvalChainRepository.findById(chainId)
                .orElseThrow(() -> new RuntimeException("Approval chain not found"));
        List<ApprovalStep> steps = approvalStepRepository.findByChainIdOrderByLevelNoAsc(chainId);

        return Map.of("chain", chain, "steps", steps);
    }

    private ApprovalStep createStep(Long chainId, Role role, int levelNo) {
        ApprovalStep step = new ApprovalStep();
        step.setChainId(chainId);
        step.setLevelNo(levelNo);
        step.setApproverRole(role);
        step.setDecision(StepDecision.PENDING);
        return step;
    }
}
