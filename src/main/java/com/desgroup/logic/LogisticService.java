package com.desgroup.logic;

import java.util.List;

import com.desgroup.exceptions.BusinessException;
import com.desgroup.interfaces.logicInterfaces.ILogisticService;
import com.desgroup.interfaces.repositoriesInterfaces.ILogisticRepository;
import com.desgroup.interfaces.rulesInterfaces.ISalaryPolicy;
import com.desgroup.interfaces.rulesInterfaces.IStaffValidator;
import com.desgroup.models.Logistic;

public class LogisticService implements ILogisticService {

    private final ILogisticRepository repository;
    private final IStaffValidator staffValidator;
    private final ISalaryPolicy<Logistic> salaryPolicy;

    public LogisticService(ILogisticRepository repository, IStaffValidator staffValidator,
            ISalaryPolicy<Logistic> salaryPolicy) {
        this.repository = repository;
        this.staffValidator = staffValidator;
        this.salaryPolicy = salaryPolicy;
    }

    @Override
    public List<Logistic> getAllLogistics() {
        return repository.getAll();
    }

    @Override
    public Logistic getLogisticById(int id) {
        Logistic logistic = repository.getById(id);
        if (logistic == null)
            throw new BusinessException("El logistico no existe.");

        return logistic;
    }

    @Override
    public void createLogistic(String name, String email, String phone, String position, String zone,
            String role, String password, int score, int workingHours) {

        validate(name, email, phone, position, password, zone, role, score, workingHours, null);

        Logistic logistic = new Logistic(0, name, email, phone, position, password, zone, role, score, workingHours);
        repository.create(logistic);
    }

    @Override
    public void updatedLogistic(int id, String name, String email, String phone, String position, String zone,
            String role, String password, int score, int workingHours) {
        Logistic logistic = getLogisticById(id);

        validate(name, email, phone, position, password, zone, role, score, workingHours, id);

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

    @Override
    public void deleteLogistic(int id) {
        getLogisticById(id);
        repository.deleteWithAssignments(id);
    }

    @Override
    public double calculateSalary(int id) {
        return salaryPolicy.calculate(getLogisticById(id));
    }

    private void validate(String name, String email, String phone, String position, String password, String zone,
            String role, int score, int workingHours, Integer currentId) {
        staffValidator.validate(name, email, phone, position, password, currentId);
        RequiredFields.requireText(zone, "zona");
        RequiredFields.requireText(role, "rol");
        if (score < 0)
            throw new BusinessException("El puntaje no puede ser negativo.");
        if (workingHours < 0)
            throw new BusinessException("Las horas trabajadas no pueden ser negativas.");
    }
}
