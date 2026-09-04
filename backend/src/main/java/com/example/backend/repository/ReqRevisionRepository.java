package com.example.backend.repository;

import com.example.backend.model.ReqRevision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReqRevisionRepository extends JpaRepository<ReqRevision, Long> {
    List<ReqRevision> findByProjectIdOrderByVersionNoDesc(Long projectId);

    Optional<ReqRevision> findFirstByProjectIdOrderByVersionNoDesc(Long projectId);
}
