package com.desgroup.logic;

import java.util.List;

import com.desgroup.exceptions.BusinessException;
import com.desgroup.interfaces.logicInterfaces.ICoordinatorService;
import com.desgroup.interfaces.repositoriesInterfaces.ICoordinatorRepository;
import com.desgroup.interfaces.rulesInterfaces.ISalaryPolicy;
import com.desgroup.interfaces.rulesInterfaces.IStaffValidator;
import com.desgroup.models.Coordinator;

public class CoordinatorService implements ICoordinatorService {

    private final ICoordinatorRepository coordinatorRepository;
    private final IStaffValidator staffValidator;
    private final ISalaryPolicy<Coordinator> salaryPolicy;

    public CoordinatorService(ICoordinatorRepository coordinatorRepository, IStaffValidator staffValidator,
            ISalaryPolicy<Coordinator> salaryPolicy) {
        this.coordinatorRepository = coordinatorRepository;
        this.staffValidator = staffValidator;
        this.salaryPolicy = salaryPolicy;
    }

    @Override
    public List<Coordinator> getAllCoordinators() {
        return coordinatorRepository.getAll();
    }

    @Override
    public Coordinator getCoordinatorById(int id) {
        Coordinator coordinator = coordinatorRepository.getById(id);
        if (coordinator == null)
            throw new BusinessException("El coordinador no existe.");

        return coordinator;
    }

    @Override
    public void createCoordinator(String name, String email, String phone, String position, String password,
            String company) {
        validate(name, email, phone, position, password, company, null);

        Coordinator coordinator = new Coordinator(name, email, phone, position, password, company);
        coordinatorRepository.create(coordinator);
    }

    @Override
    public void updatedCoordinator(int id, String name, String email, String phone,
            String position, String password, String company) {

        Coordinator coordinator = getCoordinatorById(id);

        validate(name, email, phone, position, password, company, id);

        coordinator.setName(name);
        coordinator.setEmail(email);
        coordinator.setPhone(phone);
        coordinator.setPosition(position);
        coordinator.setPassword(password);
        coordinator.setCompany(company);

        coordinatorRepository.updated(coordinator);
    }

    @Override
    public void deleteCoordinator(int id) {
        getCoordinatorById(id);
        coordinatorRepository.deleteWithAssignments(id);
    }

    @Override
    public double calculateSalary(int id) {
        return salaryPolicy.calculate(getCoordinatorById(id));
    }

    private void validate(String name, String email, String phone, String position, String password, String company,
            Integer currentId) {
        staffValidator.validate(name, email, phone, position, password, currentId);
        RequiredFields.requireText(company, "empresa");
    }

}
