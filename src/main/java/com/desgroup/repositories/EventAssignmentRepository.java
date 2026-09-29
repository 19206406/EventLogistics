package com.desgroup.repositories;

import java.util.ArrayList;
import java.util.List;

import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.models.EventAssignment;
import com.desgroup.utils.StaffRole;

public class EventAssignmentRepository implements IEventAssignmentRepository {
    private List<EventAssignment> assignments;
    private int nextId;

    public EventAssignmentRepository() {
        assignments = new ArrayList<>();
        nextId = 0;
        initializeEventAssignment();
    }

    private void initializeEventAssignment() {
        assignments.add(new EventAssignment(nextId++, 0, 1));
        assignments.add(new EventAssignment(nextId++, 0, 2));
        assignments.add(new EventAssignment(nextId++, 0, 3));
        assignments.add(new EventAssignment(nextId++, 0, 4));
    }

    public List<EventAssignment> getAll() {
        return new ArrayList<>(assignments);
    }

    public EventAssignment getById(int id) {
        for (EventAssignment assignment : assignments) {
            if (assignment.getIdEvent() == id) {
                return assignment;
            }
        }

        return null;
    }

    public List<EventAssignment> getAllByStaff(int staffId) {
        List<EventAssignment> assignmentsByStaffId = new ArrayList<>();

        for (EventAssignment assignment : assignments) {
            if (assignment.getIdStaff() == staffId) {
                assignmentsByStaffId.add(assignment);
            }
        }

        return assignmentsByStaffId;
    }

    public void create(EventAssignment assignment) {
        assignment.setIdAssignment(nextId++);
        assignments.add(assignment);
    }

    public void updated(EventAssignment assignment) {
        for (int i = 0; i < assignments.size(); i++) {
            if (assignments.get(i).getIdAssignment() == assignment.getIdAssignment()) {
                assignments.set(i, assignment);
                return;
            }
        }
    }

    public boolean exists(int staffId, int eventId) {
        for (EventAssignment assignment : assignments) {
            if (assignment.getIdStaff() == staffId && assignment.getIdEvent() == eventId) {
                return true;
            }
        }

        return false;
    }

    public void delete(int id) {
        assignments.removeIf(l -> l.getIdAssignment() == id);
    }

    @Override
    public List<EventAssignment> getAllByEvent(int eventId) {
        return assignments.stream()
                .filter(assignment -> assignment.getIdEvent() == eventId)
                .toList();
    }

    @Override
    public int countByEventAndRole(int eventId, StaffRole role) {
        return (int) assignments.stream()
                .filter(assignment -> assignment.getIdEvent() == eventId && assignment.getStaffRole() == role)
                .count();
    }

    @Override
    public int countByEventAndAssigner(int eventId, int assignerId) {
        return (int) assignments.stream()
                .filter(assignment -> assignment.getIdEvent() == eventId && assignment.getIdAssignedBy() == assignerId)
                .count();
    }

    @Override
    public void deleteByEvent(int eventId) {
        assignments.removeIf(a -> a.getIdEvent() == eventId);
    }

    @Override
    public void deleteByStaff(int staffId) {
        assignments.removeIf(a -> a.getIdStaff() == staffId);
    }

    @Override
    public void deleteByAssigner(int assignerId) {
        assignments.removeIf(a -> a.getIdAssignedBy() == assignerId);
    }

    @Override
    public void deleteByEventAndAssigner(int eventId, int assignerId) {
        assignments.removeIf(a -> a.getIdEvent() == eventId && a.getIdAssignedBy() == assignerId);
    }
}
