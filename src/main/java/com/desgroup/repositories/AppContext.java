package com.desgroup.repositories;

import java.util.List;

import com.desgroup.interfaces.logicInterfaces.*;
import com.desgroup.interfaces.repositoriesInterfaces.*;
import com.desgroup.interfaces.rulesInterfaces.AssignmentRule;
import com.desgroup.interfaces.rulesInterfaces.ISalaryPolicy;
import com.desgroup.logic.*;
import com.desgroup.logic.rules.CoordinatorCapacityRule;
import com.desgroup.logic.rules.EventNotClosedRule;
import com.desgroup.logic.rules.LogisticCapacityRule;
import com.desgroup.logic.rules.NoScheduleConflictRule;
import com.desgroup.logic.rules.NotAlreadyAssignedRule;
import com.desgroup.logic.rules.PermissionRule;
import com.desgroup.models.Coordinator;
import com.desgroup.models.Logistic;

public final class AppContext {
    private static final AppContext INSTANCE = new AppContext();
    private static final double COORDINATOR_PRICE_PER_ASSIGNMENT = 400000.00;
    private static final double LOGISTIC_HOURLY_RATE = 20000.00;

    public static AppContext get() {
        return INSTANCE;
    }

    public final IStaffService staffService;
    public final ILogisticService logisticService;
    public final ICoordinatorService coordinatorService;
    public final IEventService eventService;
    public final IEventAssignmentService assignmentService;

    private AppContext() {
        ILogisticRepository logisticRepo = new LogisticRepository();
        ICoordinatorRepository coordinatorRepo = new CoordinatorRepository();
        IStaffRepository staffRepo = new StaffRepository();
        IEventRepository eventRepo = new EventRepository();
        IEventAssignmentRepository assignmentRepo = new EventAssignmentRepository();

        ScheduleConflictChecker conflictChecker = new ScheduleConflictChecker(assignmentRepo, eventRepo);
        List<AssignmentRule> assignmentRules = List.of(
                new EventNotClosedRule(),
                new PermissionRule(),
                new NotAlreadyAssignedRule(assignmentRepo),
                new NoScheduleConflictRule(conflictChecker),
                new CoordinatorCapacityRule(assignmentRepo),
                new LogisticCapacityRule(assignmentRepo));

        ISalaryPolicy<Logistic> logistISalaryPolicy = new LogisticSalaryPolicy(LOGISTIC_HOURLY_RATE);
        ISalaryPolicy<Coordinator> coordinatorPolicy = new CoordinatorSalaryPolicy(assignmentRepo,
                COORDINATOR_PRICE_PER_ASSIGNMENT);

        StaffValidator staffValidator = new StaffValidator(staffRepo);
        EventValidator eventValidator = new EventValidator();

        staffService = new StaffService(staffRepo);
        logisticService = new LogisticService(logisticRepo, staffValidator, assignmentRepo, logistISalaryPolicy);
        coordinatorService = new CoordinatorService(coordinatorRepo, staffValidator, assignmentRepo, coordinatorPolicy);
        eventService = new EventService(eventRepo, assignmentRepo, eventValidator);
        assignmentService = new EventAssignmentService(assignmentRepo, eventRepo, logisticRepo, coordinatorRepo,
                conflictChecker, assignmentRules);
    }
}
