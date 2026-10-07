package com.desgroup.repositories;

import com.desgroup.interfaces.logicInterfaces.*;
import com.desgroup.interfaces.repositoriesInterfaces.*;
import com.desgroup.logic.*;

public final class AppContext {
    private static final AppContext INSTANCE = new AppContext();
    public static AppContext get() { return INSTANCE; }

    public final IStaffService staffService;
    public final ILogisticService logisticService;
    public final ICoordinatorService coordinatorService;
    public final IEventService eventService;
    public final IEventAssignmentService assignmentService;

    private AppContext() {
        ILogisticRepository logisticRepo = new LogisticRepository();
        ICoordinatorRepository coordinatorRepo = new CoordinatorRepository();
        IManagerRepository managerRepo = new ManagerRepository();
        IStaffRepository staffRepo = new StaffRepository(logisticRepo, coordinatorRepo, managerRepo);
        IEventRepository eventRepo = new EventRepository();
        IEventAssignmentRepository assignmentRepo = new EventAssignmentRepository();

        LogisticService logisticServiceImpl = new LogisticService(logisticRepo, assignmentRepo);

        staffService = new StaffService(staffRepo);
        logisticService = logisticServiceImpl;
        coordinatorService = new CoordinatorService(coordinatorRepo, assignmentRepo);
        eventService = new EventService(eventRepo, assignmentRepo);
        assignmentService = new EventAssignmentService(assignmentRepo, eventRepo, logisticRepo, coordinatorRepo);
    }
}
