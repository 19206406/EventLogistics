package com.desgroup.logic;

import java.util.List;

import com.desgroup.interfaces.logicInterfaces.IAvailabilityService;
import com.desgroup.interfaces.repositoriesInterfaces.IAssignmentCounter;
import com.desgroup.interfaces.repositoriesInterfaces.IAssignmentReader;
import com.desgroup.interfaces.repositoriesInterfaces.ICoordinatorRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IEventRepository;
import com.desgroup.interfaces.repositoriesInterfaces.ILogisticRepository;
import com.desgroup.interfaces.rulesInterfaces.IScheduleConflictChecker;
import com.desgroup.models.Coordinator;
import com.desgroup.models.Event;
import com.desgroup.models.Logistic;
import com.desgroup.exceptions.BusinessException;
import com.desgroup.utils.StaffRole;

public class AvailabilityService implements IAvailabilityService {

    private final ILogisticRepository logisticRepository;
    private final ICoordinatorRepository coordinatorRepository;
    private final IEventRepository eventRepository;
    private final IAssignmentReader assignmentReader;
    private final IAssignmentCounter assignmentCounter;
    private final IScheduleConflictChecker conflictChecker;

    public AvailabilityService(ILogisticRepository logisticRepository, ICoordinatorRepository coordinatorRepository,
            IEventRepository eventRepository, IAssignmentReader assignmentReader,
            IAssignmentCounter assignmentCounter, IScheduleConflictChecker conflictChecker) {
        this.logisticRepository = logisticRepository;
        this.coordinatorRepository = coordinatorRepository;
        this.eventRepository = eventRepository;
        this.assignmentReader = assignmentReader;
        this.assignmentCounter = assignmentCounter;
        this.conflictChecker = conflictChecker;
    }

    @Override
    public List<Logistic> getAvailableLogistics(int eventId) {
        Event event = requireEvent(eventId);
        return logisticRepository.getAll().stream()
                .filter(l -> !assignmentReader.exists(l.getId(), eventId))
                .filter(l -> !conflictChecker.hasConflict(l.getId(), event))
                .toList();
    }

    @Override
    public List<Coordinator> getAvailableCoordinators(int eventId) {
        Event event = requireEvent(eventId);
        return coordinatorRepository.getAll().stream()
                .filter(c -> !assignmentReader.exists(c.getId(), eventId))
                .filter(c -> !conflictChecker.hasConflict(c.getId(), event))
                .toList();
    }

    @Override
    public int getRemainingLogisticSlots(int eventId, int coordinatorId) {
        Event event = requireEvent(eventId);
        int mine = assignmentCounter.countByEventAndAssigner(eventId, coordinatorId);
        int total = assignmentCounter.countByEventAndRole(eventId, StaffRole.LOGISTIC);
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
