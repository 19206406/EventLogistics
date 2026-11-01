package com.desgroup.logic;

import com.desgroup.interfaces.repositoriesInterfaces.IAssignmentReader;
import com.desgroup.interfaces.rulesInterfaces.ISalaryPolicy;
import com.desgroup.models.Coordinator;

public class CoordinatorSalaryPolicy implements ISalaryPolicy<Coordinator> {

    private final IAssignmentReader assignmentRepository;
    private final double pricePerAssignment;

    public CoordinatorSalaryPolicy(IAssignmentReader assignmentRepository, double pricePerAssignment) {
        this.assignmentRepository = assignmentRepository;
        this.pricePerAssignment = pricePerAssignment;
    }

    @Override
    public double calculate(Coordinator staff) {
        int assignments = assignmentRepository.getAllByStaff(staff.getId()).size();

        return pricePerAssignment * assignments;
    }

}
