package com.desgroup.interfaces.rulesInterfaces;

import java.util.Set;

import com.desgroup.utils.StaffRole;

public interface RoleAssignmentRule extends AssignmentRule {
    Set<StaffRole> appliesTo();
}
