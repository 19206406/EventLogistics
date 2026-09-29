package com.desgroup.interfaces.logicInterfaces;

import java.util.List;

import com.desgroup.models.EventAssignment;
import com.desgroup.models.Staff;
import com.desgroup.models.Logistic;
import com.desgroup.models.Coordinator;

public interface IEventAssignmentService {
    List<EventAssignment> getAllAssignment();

    EventAssignment getAssignmentById(int id);

    List<EventAssignment> getAssignmentsByStaffId(int staffId);

    List<EventAssignment> getAssignmentsByEvent(int eventId);

    List<EventAssignment> getLogisticsAssignedBy(int eventId, int coordinatorId);
    
    List<Logistic> getAvailableLogistics(int eventId);

    List<Coordinator> getAvailableCoordinators(int eventId);

    int getRemainingLogisticSlots(int eventId, int coordinatorId);

    void assign(Staff actor, Staff target, int eventId);
    
    void unassign(Staff actor, int idAssignment);
}
