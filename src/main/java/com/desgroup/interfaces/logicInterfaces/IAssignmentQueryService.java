package com.desgroup.interfaces.logicInterfaces;

import java.util.List;

import com.desgroup.models.EventAssignment;
import com.desgroup.utils.StaffRole;

public interface IAssignmentQueryService {
    List<EventAssignment> getAllAssignment();

    EventAssignment getAssignmentById(int id);

    List<EventAssignment> getAssignmentsByStaffId(int staffId);

    List<EventAssignment> getAssignmentsByEvent(int eventId);

    List<EventAssignment> getLogisticsAssignedBy(int eventId, int coordinatorId);

    List<EventAssignment> getAssignmentsByEventAndRole(int eventId, StaffRole role);
}
