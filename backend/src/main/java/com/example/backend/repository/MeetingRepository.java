package com.example.backend.repository;

import com.example.backend.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByProjectIdOrderByHeldOnAsc(Long projectId);
}
