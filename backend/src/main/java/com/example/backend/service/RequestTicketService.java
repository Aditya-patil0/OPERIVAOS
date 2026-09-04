package com.example.backend.service;

import com.example.backend.model.ApprovalChain;
import com.example.backend.model.ChainStatus;
import com.example.backend.model.RequestTicket;
import com.example.backend.model.RequestType;
import com.example.backend.model.Role;
import com.example.backend.repository.RequestTicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RequestTicketService {

    private final RequestTicketRepository requestTicketRepository;
    private final ApprovalChainService approvalChainService;

    public RequestTicketService(RequestTicketRepository requestTicketRepository,
                                ApprovalChainService approvalChainService) {
        this.requestTicketRepository = requestTicketRepository;
        this.approvalChainService = approvalChainService;
    }

    public RequestTicket raiseTicket(Long projectId, RequestType type, Long raisedBy,
                                     String routedToDept, String details,
                                     List<Role> approverRolesInOrder, Integer slaHours) {
        ApprovalChain chain = approvalChainService.startChain(
                projectId, type, raisedBy, approverRolesInOrder, slaHours);

        RequestTicket ticket = new RequestTicket();
        ticket.setProjectId(projectId);
        ticket.setType(type);
        ticket.setRaisedBy(raisedBy);
        ticket.setRoutedToDept(routedToDept);
        ticket.setDetails(details);
        ticket.setChainId(chain.getId());

        return requestTicketRepository.save(ticket);
    }

    public Map<String, Object> getTicketWithStatus(Long ticketId) {
        RequestTicket ticket = requestTicketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Request ticket not found"));
        Map<String, Object> chainData = approvalChainService.getChainWithSteps(ticket.getChainId());
        ApprovalChain chain = (ApprovalChain) chainData.get("chain");
        ChainStatus chainStatus = chain.getStatus();

        return Map.of("ticket", ticket, "chainStatus", chainStatus);
    }
}
