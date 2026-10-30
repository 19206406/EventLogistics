package com.desgroup.logic;

import java.util.List;

import com.desgroup.interfaces.logicInterfaces.ILogisticService;
import com.desgroup.interfaces.logicInterfaces.IStaffSalary;
import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.interfaces.repositoriesInterfaces.ILogisticRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IStaffRepository;
import com.desgroup.models.Logistic;
import com.desgroup.models.Staff;
import com.desgroup.utils.BusinessException;

public class LogisticService implements IStaffSalary, ILogisticService {

    private final ILogisticRepository repository;
    private final IEventAssignmentRepository assignmentRepository;
    private final IStaffRepository staffRepository;
    private final double hourlyRate;

    public LogisticService(
            ILogisticRepository repository, IEventAssignmentRepository assignmentRepository,
            IStaffRepository staffRepository) {
        this.repository = repository;
        this.assignmentRepository = assignmentRepository;
        this.staffRepository = staffRepository;
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
        if (staffRepository.getByEmail(email) != null)
            throw new BusinessException("Ya existe un miembro del personal con el correo " + email + ".");

        Logistic logistic = new Logistic(0, name, email, phone, position, password, zone, role, score, workingHours);
        repository.create(logistic);
    }

    public void updatedLogistic(int id, String name, String email, String phone, String position, String zone,
            String role, String password, int score, int workingHours) {
        Logistic logistic = repository.getById(id);

        if (logistic == null)
            throw new BusinessException("El logistico no existe.");

        Staff existingEmail = staffRepository.getByEmail(email);
        if (existingEmail != null && !existingEmail.getId().equals(id))
            throw new BusinessException("Ya existe un miembro del personal con el correo " + email + ".");

        logistic.setName(name);
        logistic.setEmail(email);
        logistic.setPhone(phone);
        logistic.setPosition(position);
        logistic.setZone(zone);
        logistic.setRole(role);
        logistic.setPassword(password);
        logistic.setScore(score);
        logistic.setWorkingHours(workingHours);

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
