package com.desgroup.logic.rules;

import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.interfaces.rulesInterfaces.AssignmentRule;
import com.desgroup.models.Event;
import com.desgroup.models.Staff;
import com.desgroup.utils.BusinessException;

public class NotAlreadyAssignedRule implements AssignmentRule {

    private final IEventAssignmentRepository eventAssignmentRepository;

    public NotAlreadyAssignedRule(IEventAssignmentRepository eventAssignmentRepository) {
        this.eventAssignmentRepository = eventAssignmentRepository;
    }

    @Override
    public void check(Staff actor, Staff target, Event event) {
        if (eventAssignmentRepository.exists(target.getId(), event.getId()))
            throw new BusinessException("Esta persona ya está asignada al evento.");
    }
}
