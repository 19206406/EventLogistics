package com.desgroup.interfaces.repositoriesInterfaces;

import java.util.List;

import com.desgroup.models.EventAssignment;

public interface IAssignmentReader {
    EventAssignment getById(int id);

    List<EventAssignment> getAllByStaff(int staffId);

    List<EventAssignment> getAllByEvent(int eventId);

    boolean exists(int staffId, int eventId);
}
