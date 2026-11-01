package com.desgroup.logic;

import java.util.List;

import com.desgroup.interfaces.logicInterfaces.IAvailabilityService;
import com.desgroup.interfaces.repositoriesInterfaces.ICoordinatorRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IEventRepository;
import com.desgroup.interfaces.repositoriesInterfaces.ILogisticRepository;
import com.desgroup.models.Coordinator;
import com.desgroup.models.Event;
import com.desgroup.models.Logistic;
import com.desgroup.utils.BusinessException;
import com.desgroup.utils.StaffRole;

public class AvailabilityService implements IAvailabilityService {

    private final ILogisticRepository logisticRepository;
    private final ICoordinatorRepository coordinatorRepository;
    private final IEventRepository eventRepository;
    private final IEventAssignmentRepository eventAssignmentRepository;
    private final ScheduleConflictChecker conflictChecker;

    public AvailabilityService(ILogisticRepository logisticRepository, ICoordinatorRepository coordinatorRepository,
            IEventRepository eventRepository, IEventAssignmentRepository eventAssignmentRepository,
            ScheduleConflictChecker conflictChecker) {
        this.logisticRepository = logisticRepository;
        this.coordinatorRepository = coordinatorRepository;
        this.eventRepository = eventRepository;
        this.eventAssignmentRepository = eventAssignmentRepository;
        this.conflictChecker = conflictChecker;
    }

    @Override
    public List<Logistic> getAvailableLogistics(int eventId) {
        Event event = requireEvent(eventId);
        return logisticRepository.getAll().stream()
                .filter(l -> !eventAssignmentRepository.exists(l.getId(), eventId))
                .filter(l -> !conflictChecker.hasConflict(l.getId(), event))
                .toList();
    }

    @Override
    public List<Coordinator> getAvailableCoordinators(int eventId) {
        Event event = requireEvent(eventId);
        return coordinatorRepository.getAll().stream()
                .filter(c -> !eventAssignmentRepository.exists(c.getId(), eventId))
                .filter(c -> !conflictChecker.hasConflict(c.getId(), event))
                .toList();
    }

    @Override
    public int getRemainingLogisticSlots(int eventId, int coordinatorId) {
        Event event = requireEvent(eventId);
        int mine = eventAssignmentRepository.countByEventAndAssigner(eventId, coordinatorId);
        int total = eventAssignmentRepository.countByEventAndRole(eventId, StaffRole.LOGISTIC);
        return Math.max(0, Math.min(event.getMaxLogisticsPerCoordinator() - mine,
                event.getMaxLogistics() - total));
    }

    private Event requireEvent(int eventId) {
        Event event = eventRepository.getById(eventId);
        if (event == null)
            throw new BusinessException("El evento no existe");

        return event;
    }

}
