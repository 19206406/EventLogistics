package com.desgroup.interfaces.repositoriesInterfaces;

import java.util.function.Supplier;

import com.desgroup.models.EventAssignment;

public interface IAssignmentWriter {
    void createExclusive(int eventId, int staffId, Supplier<EventAssignment> validatedAssignment);

    void delete(int id);

    void deleteWithDelegated(int id);
}
