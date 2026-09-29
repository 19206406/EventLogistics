package com.desgroup.interfaces.repositoriesInterfaces;

import java.util.List;

import com.desgroup.models.EventAssignment;
import com.desgroup.utils.StaffRole; 

public interface IEventAssignmentRepository {
    List<EventAssignment> getAll();

    EventAssignment getById(int id);

    List<EventAssignment> getAllByStaff(int staffId);


    List<EventAssignment> getAllByEvent(int eventId);

    int countByEventAndRole(int eventId, StaffRole role);

    int countByEventAndAssigner(int eventId, int assignerId); 


    void create(EventAssignment assignment);

    void updated(EventAssignment assignment);

    boolean exists(int staffId, int eventId);

    void delete(int id);


    void deleteByEvent(int eventId);

    void deleteByStaff(int staffId);

    void deleteByAssigner(int assignerId);

    void deleteByEventAndAssigner(int eventId, int assignerId);
}
