package com.desgroup.logic.rules;

import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.interfaces.rulesInterfaces.AssignmentRule;
import com.desgroup.models.Event;
import com.desgroup.models.Staff;
import com.desgroup.utils.BusinessException;
import com.desgroup.utils.StaffRole;

public class CoordinatorCapacityRule implements AssignmentRule {

    private final IEventAssignmentRepository eventAssignmentRepository;

    public CoordinatorCapacityRule(IEventAssignmentRepository eventAssignmentRepository) {
        this.eventAssignmentRepository = eventAssignmentRepository;
    }

    @Override
    public void check(Staff actor, Staff target, Event event) {
        if (eventAssignmentRepository.countByEventAndRole(event.getId(), StaffRole.COORDINATOR) >= event
                .getMaxCoordinators())
            throw new BusinessException("El evento alcanzó el máximo de coordinadores");
    }

}
