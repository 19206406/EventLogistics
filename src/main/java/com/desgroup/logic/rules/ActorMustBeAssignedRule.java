package com.desgroup.logic.rules;

import java.util.Set;

import com.desgroup.interfaces.repositoriesInterfaces.IAssignmentReader;
import com.desgroup.interfaces.rulesInterfaces.RoleAssignmentRule;
import com.desgroup.models.Event;
import com.desgroup.models.Staff;
import com.desgroup.utils.BusinessException;
import com.desgroup.utils.StaffRole;

public class ActorMustBeAssignedRule implements RoleAssignmentRule {

    private final IAssignmentReader eventAssignmentRepository;

    public ActorMustBeAssignedRule(IAssignmentReader eventAssignmentRepository) {
        this.eventAssignmentRepository = eventAssignmentRepository;
    }

    @Override
    public Set<StaffRole> appliesTo() {
        return Set.of(StaffRole.LOGISTIC);
    }

    @Override
    public void check(Staff actor, Staff target, Event event) {
        if (!eventAssignmentRepository.exists(actor.getId(), event.getId()))
            throw new BusinessException("Usted no está asignado a este evento");
    }
}
