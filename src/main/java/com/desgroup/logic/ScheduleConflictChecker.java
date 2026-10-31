package com.desgroup.logic;

import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IEventRepository;
import com.desgroup.models.Event;
import com.desgroup.models.EventAssignment;

public class ScheduleConflictChecker {

    private final IEventAssignmentRepository eventAssignmentRepository;
    private final IEventRepository eventRepository;

    public ScheduleConflictChecker(IEventAssignmentRepository eventAssignmentRepository,
            IEventRepository eventRepository) {
        this.eventAssignmentRepository = eventAssignmentRepository;
        this.eventRepository = eventRepository;
    }

    public boolean hasConflict(int staffId, Event newEvent) {
        for (EventAssignment a : eventAssignmentRepository.getAllByStaff(staffId)) {
            Event other = eventRepository.getById(a.getEvent().getId());
            if (other != null && other.isClosed() && overlaps(other, newEvent))
                return true;
        }
        return false;
    }

    private boolean overlaps(Event a, Event b) {
        return a.getDate().equals(b.getDate())
                && a.getStartTime() < b.getEndTime()
                && b.getStartTime() < a.getEndTime();
    }
}
