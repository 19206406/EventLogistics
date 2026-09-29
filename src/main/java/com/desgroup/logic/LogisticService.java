package com.desgroup.logic;

import java.util.List;

import com.desgroup.interfaces.logicInterfaces.ILogisticService;
import com.desgroup.interfaces.logicInterfaces.IStaffSalary;
import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.interfaces.repositoriesInterfaces.ILogisticRepository;
import com.desgroup.models.Logistic;

public class LogisticService implements IStaffSalary, ILogisticService {

    private final ILogisticRepository repository;
    private final IEventAssignmentRepository assignmentRepository;
    private final double hourlyRate;

    public LogisticService(ILogisticRepository repository, IEventAssignmentRepository assignmentRepository) {
        this.repository = repository;
        this.assignmentRepository = assignmentRepository;
        hourlyRate = 10000.00;
    }

    public List<Logistic> getAllLogistics() {
        return repository.getAll();
    }

    public Logistic getLogisticById(int id) {
        return repository.getById(id);
    }

    public void createLogistic(String name, String email, String phone, String position, String zone,
            String role, String password, int score, int workingHours) {
        Logistic logistic = new Logistic(0, name, email, phone, position, password, zone, role, score, workingHours);
        repository.create(logistic);
    }

    public void updatedLogistic(int id, String name, String email, String phone, String position, String zone,
            String role, String password, int score, int workingHours) {
        Logistic logistic = new Logistic(id, name, email, phone, position, password, zone, role, score, workingHours);
        repository.updated(logistic);
    }

    public void deleteLogistic(int id) {
        assignmentRepository.deleteByStaff(id);
        repository.delete(id);
    }

    public void recordHoursWorked(int id, int hours) {
        Logistic searchLogistic = repository.getById(id);
        searchLogistic.setWorkingHours(hours);
        repository.updated(searchLogistic);
    }

    @Override
    public double calculateSalary(int id) {
        Logistic logistic = repository.getById(id);
        int hoursWorked = logistic.getWorkingHours();
        double salary = hoursWorked * hourlyRate;
        logistic.setSalary(salary);
        repository.updated(logistic);

        return salary;
    }
}
