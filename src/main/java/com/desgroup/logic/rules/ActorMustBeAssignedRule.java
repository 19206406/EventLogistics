package com.desgroup.logic.rules;

import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.interfaces.rulesInterfaces.AssignmentRule;
import com.desgroup.models.Event;
import com.desgroup.models.Staff;
import com.desgroup.utils.BusinessException;

public class ActorMustBeAssignedRule implements AssignmentRule {

    private final IEventAssignmentRepository eventAssignmentRepository;

    public ActorMustBeAssignedRule(IEventAssignmentRepository eventAssignmentRepository) {
        this.eventAssignmentRepository = eventAssignmentRepository;
    }

    @Override
    public void check(Staff actor, Staff target, Event event) {
        if (!eventAssignmentRepository.exists(actor.getId(), event.getId()))
            throw new BusinessException("Usted no está asignado a este evento");
    }
}
