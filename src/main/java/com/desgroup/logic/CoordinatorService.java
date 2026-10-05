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
    private final double priceAssignment;

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

    public void updatedCoordinator(int id, String name, String email, String phone,
                                   String position, String password, String company) {
        coordinatorRepository.updated(new Coordinator(id, name, email, phone, position, password, company));
    }

    public void deleteCoordinator(int id) {
        assignmentRepository.deleteByAssigner(id);
        assignmentRepository.deleteByStaff(id);
        coordinatorRepository.delete(id);
    }

    @Override
    public double calculateSalary(int id) {
        List<EventAssignment> assignments = assignmentRepository.getAllByStaff(id);
        Coordinator coordinator = coordinatorRepository.getById(id);
        double salary = priceAssignment * assignments.size();
        coordinator.setSalary(salary);
        coordinatorRepository.updated(coordinator);
        return salary;
    }

}
