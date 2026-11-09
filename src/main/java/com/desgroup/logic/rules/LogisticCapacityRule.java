package com.desgroup.logic.rules;

import java.util.Set;

import com.desgroup.interfaces.repositoriesInterfaces.IAssignmentCounter;
import com.desgroup.interfaces.rulesInterfaces.RoleAssignmentRule;
import com.desgroup.models.Event;
import com.desgroup.models.Staff;
import com.desgroup.exceptions.BusinessException;
import com.desgroup.utils.StaffRole;

public class LogisticCapacityRule implements RoleAssignmentRule {

    private final IAssignmentCounter eventAssignmentRepository;

    public LogisticCapacityRule(IAssignmentCounter eventAssignmentRepository) {
        this.eventAssignmentRepository = eventAssignmentRepository;
    }

    @Override
    public Set<StaffRole> appliesTo() {
        return Set.of(StaffRole.LOGISTIC);
    }

    @Override
    public void check(Staff actor, Staff target, Event event) {
        int eventId = event.getId();
        if (eventAssignmentRepository.countByEventAndRole(eventId, StaffRole.LOGISTIC) >= event.getMaxLogistics())
            throw new BusinessException("El evento alcanzó el máximo de logísticos");
        if (eventAssignmentRepository.countByEventAndAssigner(eventId, actor.getId()) >= event
                .getMaxLogisticsPerCoordinator())
            throw new BusinessException("Alcanzó su máximo de logísticos para este evento.");
    }

}
