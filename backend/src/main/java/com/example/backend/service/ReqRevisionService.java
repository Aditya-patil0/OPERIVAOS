package com.example.backend.service;

import com.example.backend.model.ReqRevision;
import com.example.backend.repository.ReqRevisionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReqRevisionService {

    private final ReqRevisionRepository reqRevisionRepository;

    public ReqRevisionService(ReqRevisionRepository reqRevisionRepository) {
        this.reqRevisionRepository = reqRevisionRepository;
    }

    public ReqRevision addRevision(Long projectId, String description, Long addedBy) {
        Optional<ReqRevision> currentRevision =
                reqRevisionRepository.findFirstByProjectIdOrderByVersionNoDesc(projectId);

        ReqRevision revision = new ReqRevision();
        revision.setProjectId(projectId);
        revision.setDescription(description);
        revision.setAddedBy(addedBy);
        revision.setVersionNo(currentRevision.map(existing -> existing.getVersionNo() + 1).orElse(1));
        revision.setSupersedesId(currentRevision.map(ReqRevision::getId).orElse(null));

        return reqRevisionRepository.save(revision);
    }

    public Optional<ReqRevision> getCurrentRevision(Long projectId) {
        return reqRevisionRepository.findFirstByProjectIdOrderByVersionNoDesc(projectId);
    }

    public List<ReqRevision> getFullHistory(Long projectId) {
        return reqRevisionRepository.findByProjectIdOrderByVersionNoDesc(projectId);
    }
}
