package com.desgroup.logic;

import java.util.List;

import com.desgroup.models.EventAssignment;
import com.desgroup.repositories.EventAssignmentRepository;

public class EventAssignmentService {

    private EventAssignmentRepository repository;

    public EventAssignmentService() {
        repository = new EventAssignmentRepository();
    }

    public List<EventAssignment> getAllAssignment() {
        return repository.getAll();
    }

    public EventAssignment getAssignmentById(int id) {
        return repository.getById(id);
    }

    public void createAssignment(int idStaff, int idEvent) {

        // si existe una asignación con este id no debería poder
        // crear otro
        if (repository.exists(idStaff, idEvent))
            return;

        EventAssignment newAssignment = new EventAssignment(0, idStaff, idEvent);
        repository.create(newAssignment);
    }

    public void deleteAssignment(int id) {
        repository.delete(id);
    }

    public List<EventAssignment> getAssignmentsByStaffId(int staffId) {
        return repository.getAllByStaff(staffId);
    }
}
