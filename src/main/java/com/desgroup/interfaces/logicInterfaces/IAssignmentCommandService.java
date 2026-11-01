package com.desgroup.interfaces.logicInterfaces;

import com.desgroup.models.Staff;

public interface IAssignmentCommandService {

    void assign(Staff actor, Staff target, int eventId);

    void unassign(Staff actor, int idAssignment);
}
