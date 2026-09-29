package com.desgroup.repositories;

import com.desgroup.interfaces.repositoriesInterfaces.*;
import com.desgroup.logic.*;
import com.desgroup.models.Coordinator;
import com.desgroup.models.Logistic;
import com.desgroup.models.Manager;

public final class AppContext {
    private static final AppContext INSTANCE = new AppContext();
    public static AppContext get() { return INSTANCE; }

    public final StaffService staffService;
    public final LogisticService logisticService;
    public final CoordinatorService coordinatorService;
    public final EventService eventService;
    public final EventAssignmentService assignmentService;

    private AppContext() {
        ILogisticRepository logisticRepo = new LogisticRepository();
        ICoordinatorRepository coordinatorRepo = new CoordinatorRepository();
        IManagerRepository managerRepo = new ManagerRepository();
        IStaffRepository staffRepo = new StaffRepository(logisticRepo, coordinatorRepo, managerRepo);
        IEventRepository eventRepo = new EventRepository();
        IEventAssignmentRepository assignmentRepo = new EventAssignmentRepository();

        staffService = new StaffService(staffRepo);
        logisticService = new LogisticService(logisticRepo, assignmentRepo);
        coordinatorService = new CoordinatorService(coordinatorRepo, assignmentRepo);
        eventService = new EventService(eventRepo, assignmentRepo);
        assignmentService = new EventAssignmentService(assignmentRepo, eventRepo, logisticRepo, coordinatorRepo);

        // seed usando las reglas reales, con los usuarios del README
        int eventId = eventRepo.getAll().get(0).getIdEvent();
        Manager admin = managerRepo.getByEmail("admin");
        Coordinator robert = coordinatorRepo.getByEmail("robert@gmail.com");
        Logistic sebastian = logisticRepo.getByEmail("sebastian@gmail.com");
        assignmentService.assign(admin, robert, eventId);
        assignmentService.assign(robert, sebastian, eventId);
    }
}
