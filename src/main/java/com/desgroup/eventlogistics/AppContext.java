package com.desgroup.eventlogistics;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.desgroup.interfaces.logicInterfaces.*;
import com.desgroup.interfaces.repositoriesInterfaces.*;
import com.desgroup.interfaces.rulesInterfaces.AssignmentRule;
import com.desgroup.interfaces.rulesInterfaces.IEventValidator;
import com.desgroup.interfaces.rulesInterfaces.IScheduleConflictChecker;
import com.desgroup.interfaces.rulesInterfaces.ISalaryPolicy;
import com.desgroup.interfaces.rulesInterfaces.IStaffValidator;
import com.desgroup.interfaces.rulesInterfaces.RoleAssignmentRule;
import com.desgroup.logic.*;
import com.desgroup.logic.rules.ActorMustBeAssignedRule;
import com.desgroup.logic.rules.CoordinatorCapacityRule;
import com.desgroup.logic.rules.EventNotClosedRule;
import com.desgroup.logic.rules.LogisticCapacityRule;
import com.desgroup.logic.rules.NoScheduleConflictRule;
import com.desgroup.logic.rules.NotAlreadyAssignedRule;
import com.desgroup.logic.rules.PermissionRule;
import com.desgroup.models.Coordinator;
import com.desgroup.models.Logistic;
import com.desgroup.repositories.CoordinatorRepository;
import com.desgroup.repositories.EventAssignmentRepository;
import com.desgroup.repositories.EventRepository;
import com.desgroup.repositories.LogisticRepository;
import com.desgroup.repositories.ManagerRepository;
import com.desgroup.repositories.StaffRepository;
import com.desgroup.utils.StaffRole;

public final class AppContext {
    private static final double COORDINATOR_PRICE_PER_ASSIGNMENT = 400000.00;
    private static final double LOGISTIC_HOURLY_RATE = 20000.00;

    public final IStaffService staffService;
    public final ILogisticService logisticService;
    public final ICoordinatorService coordinatorService;
    public final IEventService eventService;
    public final IAssignmentCommandService assignmentCommandService;
    public final IAssignmentQueryService assignmentQueryService;
    public final IAvailabilityService availabilityService;

    public final IManagerService managerService;

    public AppContext(SessionFactory sessionFactory) {
        ILogisticRepository logisticRepo = new LogisticRepository(sessionFactory);
        ICoordinatorRepository coordinatorRepo = new CoordinatorRepository(sessionFactory);
        IStaffRepository staffRepo = new StaffRepository(sessionFactory);
        IEventRepository eventRepo = new EventRepository(sessionFactory);
        IEventAssignmentRepository assignmentRepo = new EventAssignmentRepository(sessionFactory);
        IManagerRepository managerRepo = new ManagerRepository(sessionFactory);

        IScheduleConflictChecker conflictChecker = new ScheduleConflictChecker(assignmentRepo, eventRepo);
        List<AssignmentRule> commonRules = List.of(
                new EventNotClosedRule(),
                new PermissionRule(),
                new NotAlreadyAssignedRule(assignmentRepo),
                new NoScheduleConflictRule(conflictChecker));

        List<RoleAssignmentRule> roleRules = List.of(
                new CoordinatorCapacityRule(assignmentRepo),
                new ActorMustBeAssignedRule(assignmentRepo),
                new LogisticCapacityRule(assignmentRepo));

        Map<StaffRole, List<AssignmentRule>> rulesByRole = new EnumMap<>(StaffRole.class);
        for (RoleAssignmentRule rule : roleRules) {
            for (StaffRole role : rule.appliesTo()) {
                rulesByRole.computeIfAbsent(role, r -> new ArrayList<>()).add(rule);
            }
        }

        ISalaryPolicy<Logistic> logistISalaryPolicy = new LogisticSalaryPolicy(LOGISTIC_HOURLY_RATE);
        ISalaryPolicy<Coordinator> coordinatorPolicy = new CoordinatorSalaryPolicy(assignmentRepo,
                COORDINATOR_PRICE_PER_ASSIGNMENT);

        IStaffValidator staffValidator = new StaffValidator(staffRepo);
        IEventValidator eventValidator = new EventValidator();

        staffService = new StaffService(staffRepo);
        logisticService = new LogisticService(logisticRepo, staffValidator, logistISalaryPolicy);
        coordinatorService = new CoordinatorService(coordinatorRepo, staffValidator, coordinatorPolicy);
        eventService = new EventService(eventRepo, assignmentRepo, eventValidator);

        assignmentCommandService = new AssignmentCommandService(assignmentRepo, assignmentRepo, eventRepo,
                commonRules, rulesByRole);
        assignmentQueryService = new AssignmentQueryService(assignmentRepo);
        availabilityService = new AvailabilityService(logisticRepo, coordinatorRepo, eventRepo, assignmentRepo,
                assignmentRepo, conflictChecker);

        managerService = new ManagerService(managerRepo);
    }
}
