package com.desgroup.interfaces.rulesInterfaces;

import com.desgroup.models.Event;
import com.desgroup.models.Staff;

public interface AssignmentRule {
    void check(Staff actor, Staff target, Event event);
}
