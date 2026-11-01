package com.desgroup.logic.rules;

import com.desgroup.interfaces.rulesInterfaces.AssignmentRule;
import com.desgroup.interfaces.rulesInterfaces.IScheduleConflictChecker;
import com.desgroup.models.Event;
import com.desgroup.models.Staff;
import com.desgroup.utils.BusinessException;

public class NoScheduleConflictRule implements AssignmentRule {

    private final IScheduleConflictChecker conflictChecker;

    public NoScheduleConflictRule(IScheduleConflictChecker conflictChecker) {
        this.conflictChecker = conflictChecker;
    }

    @Override
    public void check(Staff actor, Staff target, Event event) {
        if (conflictChecker.hasConflict(target.getId(), event))
            throw new BusinessException("Esta persona tiene otro evento que se cruza");
    }

}
