package com.desgroup.logic.rules;

import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.interfaces.rulesInterfaces.AssignmentRule;
import com.desgroup.models.Event;
import com.desgroup.models.Staff;
import com.desgroup.utils.BusinessException;
import com.desgroup.utils.StaffRole;

public class LogisticCapacityRule implements AssignmentRule {

    private final IEventAssignmentRepository eventAssignmentRepository;

    public LogisticCapacityRule(IEventAssignmentRepository eventAssignmentRepository) {
        this.eventAssignmentRepository = eventAssignmentRepository;
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
