package com.desgroup.logic;

import java.util.List;

import com.desgroup.interfaces.logicInterfaces.ICoordinatorService;
import com.desgroup.interfaces.repositoriesInterfaces.ICoordinatorRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IAssignmentWriter;
import com.desgroup.interfaces.rulesInterfaces.ISalaryPolicy;
import com.desgroup.interfaces.rulesInterfaces.IStaffValidator;
import com.desgroup.models.Coordinator;
import com.desgroup.utils.BusinessException;

public class CoordinatorService implements ICoordinatorService {

    private final ICoordinatorRepository coordinatorRepository;
    private final IStaffValidator staffValidator;
    private final IAssignmentWriter assignmentRepository;
    private final ISalaryPolicy<Coordinator> salaryPolicy;

    public CoordinatorService(ICoordinatorRepository coordinatorRepository, IStaffValidator staffValidator,
            IAssignmentWriter assignmentRepository, ISalaryPolicy<Coordinator> salaryPolicy) {
        this.coordinatorRepository = coordinatorRepository;
        this.staffValidator = staffValidator;
        this.assignmentRepository = assignmentRepository;
        this.salaryPolicy = salaryPolicy;
    }

    @Override
    public List<Coordinator> getAllCoordinators() {
        return coordinatorRepository.getAll();
    }

    @Override
    public Coordinator getCoordinatorById(int id) {
        return coordinatorRepository.getById(id);
    }

    @Override
    public void createCoordinator(String name, String email, String phone, String position, String password,
            String company) {
        validate(name, email, phone, password, company, null);

        Coordinator coordinator = new Coordinator(name, email, phone, position, password, company);
        coordinatorRepository.create(coordinator);
    }

    @Override
    public void updatedCoordinator(int id, String name, String email, String phone,
            String position, String password, String company) {

        Coordinator coordinator = coordinatorRepository.getById(id);

        if (coordinator == null)
            throw new BusinessException("El coordinador no existe.");

        validate(name, email, phone, password, company, id);

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
        assignmentRepository.deleteByAssigner(id);
        assignmentRepository.deleteByStaff(id);
        coordinatorRepository.delete(id);
    }

    @Override
    public double calculateSalary(int id) {
        Coordinator coordinator = coordinatorRepository.getById(id);
        if (coordinator == null)
            throw new BusinessException("El coordinador no existe");

        return salaryPolicy.calculate(coordinator);
    }

    private void validate(String name, String email, String phone, String password, String company,
            Integer currentId) {
        staffValidator.validate(name, email, phone, password, currentId);
        RequiredFields.requireNotBlank(company, "empresa");
    }

}
