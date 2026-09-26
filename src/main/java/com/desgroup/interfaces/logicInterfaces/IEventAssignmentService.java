package com.desgroup.interfaces.logicInterfaces;

import java.util.List;

import com.desgroup.models.EventAssignment;

public interface IEventAssignmentService {
    List<EventAssignment> getAllAssignment();

    EventAssignment getAssignmentById(int id);

    void createAssignment(int idStaff, int idEvent);

    void deleteAssignment(int id);

    List<EventAssignment> getAssignmentsByStaffId(int staffId);
}
