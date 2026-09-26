package com.desgroup.interfaces.repositoriesInterfaces;

import java.util.List;

import com.desgroup.models.EventAssignment;

public interface IEventAssignmentRepository {
    List<EventAssignment> getAll();

    EventAssignment getById(int id);

    List<EventAssignment> getAllByStaff(int staffId);

    void create(EventAssignment assignment);

    void updated(EventAssignment assignment);

    boolean exists(int staffId, int eventId);

    void delete(int id);
}
