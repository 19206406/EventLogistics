package com.desgroup.logic;

import java.util.List;

import com.desgroup.interfaces.IStaffSalary;
import com.desgroup.models.Logistic;
import com.desgroup.repositories.LogisticRepository;

public class LogisticService implements IStaffSalary {

    private LogisticRepository repository;
    private double hourlyRate;

    public LogisticService() {
        repository = new LogisticRepository();
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
