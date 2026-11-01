package com.desgroup.interfaces.rulesInterfaces;

import com.desgroup.models.Event;

public interface IScheduleConflictChecker {
    boolean hasConflict(int staffId, Event newEvent);
}
