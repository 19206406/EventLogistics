package com.desgroup.logic.rules;

import com.desgroup.interfaces.rulesInterfaces.AssignmentRule;
import com.desgroup.models.Event;
import com.desgroup.models.Staff;
import com.desgroup.exceptions.BusinessException;

public class PermissionRule implements AssignmentRule {

    @Override
    public void check(Staff actor, Staff target, Event event) {
        if (!actor.canAssign(target))
            throw new BusinessException("Usted no tiene permiso para asignar a esta persona");
    }
}
