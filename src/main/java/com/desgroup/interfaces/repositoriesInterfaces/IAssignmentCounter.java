package com.desgroup.interfaces.repositoriesInterfaces;

import com.desgroup.utils.StaffRole;

public interface IAssignmentCounter {
    int countByEventAndRole(int eventId, StaffRole role);

    int countByEventAndAssigner(int eventId, int assignerId);
}
