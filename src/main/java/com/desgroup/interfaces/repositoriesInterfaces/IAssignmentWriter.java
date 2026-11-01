package com.desgroup.interfaces.repositoriesInterfaces;

import com.desgroup.models.EventAssignment;

public interface IAssignmentWriter {
    void create(EventAssignment assignment);

    void delete(int id);

    void deleteByEvent(int eventId);

    void deleteByStaff(int staffId);

    void deleteByAssigner(int assignerId);

    void deleteByEventAndAssigner(int eventId, int assignerId);
}
