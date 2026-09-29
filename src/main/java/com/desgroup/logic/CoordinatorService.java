package com.desgroup.logic;

import java.util.List;

import com.desgroup.interfaces.logicInterfaces.ICoordinatorService;
import com.desgroup.interfaces.logicInterfaces.IStaffSalary;
import com.desgroup.interfaces.repositoriesInterfaces.ICoordinatorRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.models.Coordinator;
import com.desgroup.models.EventAssignment;

public class CoordinatorService implements IStaffSalary, ICoordinatorService {

    private final ICoordinatorRepository coordinatorRepository;
    private final IEventAssignmentRepository assignmentRepository;
    private double priceAssignment;

    public CoordinatorService(ICoordinatorRepository coordinatorRepository,
            IEventAssignmentRepository assignmentRepository) {
        this.coordinatorRepository = coordinatorRepository;
        this.assignmentRepository = assignmentRepository;
        priceAssignment = 400000.00;
    }

    public List<Coordinator> getAllCoordinators() {
        return coordinatorRepository.getAll();
    }

    public Coordinator getCoordinatorById(int id) {
        return coordinatorRepository.getById(id);
    }

    public void createCoordinator(String name, String email, String phone, String position, String password,
            String company) {
        Coordinator coordinator = new Coordinator(0, name, email, phone, position, password, company);
        coordinatorRepository.create(coordinator);
    }

    public void updatedCoordinator(String name, String email, String phone, String position, String password,
            String company) {
        Coordinator coordinator = new Coordinator(0, name, email, phone, position, password, company);
        coordinatorRepository.updated(coordinator);
    }

    public void deleteCoordinator(int id) {
        coordinatorRepository.delete(id);
    }

    @Override
    public double calculateSalary(int id) {
        List<EventAssignment> assignments = assignmentRepository.getAllByStaff(id);

        return priceAssignment * assignments.size();
    }

}
