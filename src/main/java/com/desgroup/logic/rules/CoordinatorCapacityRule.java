package com.desgroup.logic.rules;

import java.util.Set;

import com.desgroup.interfaces.repositoriesInterfaces.IAssignmentCounter;
import com.desgroup.interfaces.rulesInterfaces.RoleAssignmentRule;
import com.desgroup.models.Event;
import com.desgroup.models.Staff;
import com.desgroup.exceptions.BusinessException;
import com.desgroup.utils.StaffRole;

public class CoordinatorCapacityRule implements RoleAssignmentRule {

    private final IAssignmentCounter eventAssignmentRepository;

    public CoordinatorCapacityRule(IAssignmentCounter eventAssignmentRepository) {
        this.eventAssignmentRepository = eventAssignmentRepository;
    }

    @Override
    public Set<StaffRole> appliesTo() {
        return Set.of(StaffRole.COORDINATOR);
    }

    @Override
    public void check(Staff actor, Staff target, Event event) {
        if (eventAssignmentRepository.countByEventAndRole(event.getId(), StaffRole.COORDINATOR) >= event
                .getMaxCoordinators())
            throw new BusinessException("El evento alcanzó el máximo de coordinadores");
    }

}
