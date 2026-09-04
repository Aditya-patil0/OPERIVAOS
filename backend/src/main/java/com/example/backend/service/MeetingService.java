package com.example.backend.service;

import com.example.backend.model.Meeting;
import com.example.backend.repository.MeetingRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public Meeting logMeeting(Long projectId, String purpose, String notes, Instant heldOn,
                              Long loggedBy, String stageTag) {
        Meeting meeting = new Meeting();
        meeting.setProjectId(projectId);
        meeting.setPurpose(purpose);
        meeting.setNotes(notes);
        meeting.setHeldOn(heldOn);
        meeting.setLoggedBy(loggedBy);
        meeting.setStageTag(stageTag);

        return meetingRepository.save(meeting);
    }

    public List<Meeting> getMeetingsForProject(Long projectId) {
        return meetingRepository.findByProjectIdOrderByHeldOnAsc(projectId);
    }
}
