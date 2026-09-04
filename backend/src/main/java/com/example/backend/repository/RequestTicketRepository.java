package com.example.backend.repository;

import com.example.backend.model.RequestTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestTicketRepository extends JpaRepository<RequestTicket, Long> {
    List<RequestTicket> findByProjectId(Long projectId);
}
