package com.desgroup.interfaces.logicInterfaces;

import com.desgroup.models.Staff;

public interface IAssignmentCommandService {
    boolean canAssign(Staff actor, Staff target, int eventId);

    void assign(Staff actor, Staff target, int eventId);

    void unassign(Staff actor, int idAssignment);
}
