package com.desgroup.logic;

import java.util.List;

import com.desgroup.interfaces.logicInterfaces.IEventAssignmentService;
import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.models.EventAssignment;

public class EventAssignmentService implements IEventAssignmentService {

    private final IEventAssignmentRepository repository;

    public EventAssignmentService(IEventAssignmentRepository repository) {
        this.repository = repository;
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
