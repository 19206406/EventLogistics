package com.desgroup.logic;

import java.util.List;

import com.desgroup.interfaces.IStaffSalary;
import com.desgroup.interfaces.logicInterfaces.ICoordinatorService;
import com.desgroup.models.Coordinator;
import com.desgroup.models.EventAssignment;
import com.desgroup.repositories.CoordinatorRepository;
import com.desgroup.repositories.EventAssignmentRepository;

public class CoordinatorService implements IStaffSalary, ICoordinatorService {

    private CoordinatorRepository repository;
    private EventAssignmentRepository assignmentRepository;
    private double priceAssignment;

    public CoordinatorService() {
        repository = new CoordinatorRepository();
        assignmentRepository = new EventAssignmentRepository();
        priceAssignment = 400000.00;
    }

    public List<Coordinator> getAllCoordinators() {
        return repository.getAll();
    }

    public Coordinator getCoordinatorById(int id) {
        return repository.getById(id);
    }

    public void createCoordinator(String name, String email, String phone, String position, String password,
            String company) {
        Coordinator coordinator = new Coordinator(0, name, email, phone, position, password, company);
        repository.create(coordinator);
    }

    public void updatedCoordinator(String name, String email, String phone, String position, String password,
            String company) {
        Coordinator coordinator = new Coordinator(0, name, email, phone, position, password, company);
        repository.updated(coordinator);
    }

    public void deleteCoordinator(int id) {
        repository.delete(id);
    }

    @Override
    public double calculateSalary(int id) {
        List<EventAssignment> assignments = assignmentRepository.getAllByStaff(id);

        return priceAssignment * assignments.size();
    }

}
