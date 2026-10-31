package com.desgroup.logic;

import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.interfaces.rulesInterfaces.ISalaryPolicy;
import com.desgroup.models.Coordinator;

public class CoordinatorSalaryPolicy implements ISalaryPolicy<Coordinator> {

    private final IEventAssignmentRepository assignmentRepository;
    private final double pricePerAssignment;

    public CoordinatorSalaryPolicy(IEventAssignmentRepository assignmentRepository, double pricePerAssignment) {
        this.assignmentRepository = assignmentRepository;
        this.pricePerAssignment = pricePerAssignment;
    }

    @Override
    public double calculate(Coordinator staff) {
        int assignments = assignmentRepository.getAllByStaff(staff.getId()).size();

        return pricePerAssignment * assignments;
    }

}
