package com.desgroup.logic.rules;

import com.desgroup.interfaces.rulesInterfaces.AssignmentRule;
import com.desgroup.models.Event;
import com.desgroup.models.Staff;
import com.desgroup.exceptions.BusinessException;

public class EventNotClosedRule implements AssignmentRule {
    @Override
    public void check(Staff actor, Staff target, Event event) {
        if (event.isClosed())
            throw new BusinessException("El evento está finalizado o cancelado");
    }
}
