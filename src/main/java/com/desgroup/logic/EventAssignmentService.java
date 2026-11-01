package com.desgroup.logic;

import java.util.List;
import java.util.Map;

import com.desgroup.interfaces.logicInterfaces.IEventAssignmentService;
import com.desgroup.interfaces.repositoriesInterfaces.ICoordinatorRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IEventRepository;
import com.desgroup.interfaces.repositoriesInterfaces.ILogisticRepository;
import com.desgroup.interfaces.rulesInterfaces.AssignmentRule;
import com.desgroup.models.Coordinator;
import com.desgroup.models.Event;
import com.desgroup.models.EventAssignment;
import com.desgroup.models.Logistic;
import com.desgroup.models.Staff;
import com.desgroup.utils.BusinessException;
import com.desgroup.utils.StaffRole;

public class EventAssignmentService implements IEventAssignmentService {

    private final IEventAssignmentRepository assignmentRepository;
    private final IEventRepository eventRepository;
    private final ILogisticRepository logisticRepository;
    private final ICoordinatorRepository coordinatorRepository;
    private final ScheduleConflictChecker conflictChecker;
    private final List<AssignmentRule> commonRules;
    private final Map<StaffRole, List<AssignmentRule>> rulesByRole;

    public EventAssignmentService(IEventAssignmentRepository assignmentRepository, IEventRepository eventRepository,
            ILogisticRepository logisticRepository, ICoordinatorRepository coordinatorRepository,
            ScheduleConflictChecker conflictChecker, List<AssignmentRule> commonRules,
            Map<StaffRole, List<AssignmentRule>> rulesByRole) {
        this.assignmentRepository = assignmentRepository;
        this.eventRepository = eventRepository;
        this.logisticRepository = logisticRepository;
        this.coordinatorRepository = coordinatorRepository;
        this.conflictChecker = conflictChecker;
        this.commonRules = commonRules;
        this.rulesByRole = rulesByRole;
    }

    @Override
    public List<EventAssignment> getAllAssignment() {
        return assignmentRepository.getAll();
    }

    @Override
    public EventAssignment getAssignmentById(int id) {
        return assignmentRepository.getById(id);
    }

    @Override
    public List<EventAssignment> getAssignmentsByStaffId(int staffId) {
        return assignmentRepository.getAllByStaff(staffId);
    }

    @Override
    public List<EventAssignment> getAssignmentsByEvent(int eventId) {
        return assignmentRepository.getAllByEvent(eventId);
    }

    @Override
    public List<EventAssignment> getLogisticsAssignedBy(int eventId, int coordinatorId) {
        return assignmentRepository.getAllByEvent(eventId).stream()
                .filter(a -> a.getAssignedBy().getId().equals(coordinatorId)
                        && a.getStaffRole() == StaffRole.LOGISTIC)
                .toList();
    }

    @Override
    public List<EventAssignment> getAssignmentsByEventAndRole(int eventId, StaffRole role) {
        return assignmentRepository.getAllByEvent(eventId).stream()
                .filter(a -> a.getStaffRole() == role)
                .toList();
    }

    @Override
    public List<Logistic> getAvailableLogistics(int eventId) {
        Event event = requireEvent(eventId);
        return logisticRepository.getAll().stream()
                .filter(l -> !assignmentRepository.exists(l.getId(), eventId))
                .filter(l -> !conflictChecker.hasConflict(l.getId(), event))
                .toList();
    }

    @Override
    public List<Coordinator> getAvailableCoordinators(int eventId) {
        Event event = requireEvent(eventId);
        return coordinatorRepository.getAll().stream()
                .filter(c -> !assignmentRepository.exists(c.getId(), eventId))
                .filter(c -> !conflictChecker.hasConflict(c.getId(), event))
                .toList();
    }

    @Override
    public int getRemainingLogisticSlots(int eventId, int coordinatorId) {
        Event event = requireEvent(eventId);
        int mine = assignmentRepository.countByEventAndAssigner(eventId, coordinatorId);
        int total = assignmentRepository.countByEventAndRole(eventId, StaffRole.LOGISTIC);
        return Math.max(0, Math.min(event.getMaxLogisticsPerCoordinator() - mine,
                event.getMaxLogistics() - total));
    }

    @Override
    public boolean canAssign(Staff actor, Staff target, int eventId) {
        try {
            validateAssignment(actor, target, eventId);
            return true;
        } catch (BusinessException e) {
            return false;
        }
    }

    @Override
    public void assign(Staff actor, Staff target, int eventId) {
        Event event = validateAssignment(actor, target, eventId);
        assignmentRepository.create(new EventAssignment(target, event, actor, target.getStaffRole()));
    }

    @Override
    public void unassign(Staff actor, int idAssignment) {
        EventAssignment assignment = assignmentRepository.getById(idAssignment);
        if (assignment == null)
            throw new BusinessException("La asignación no existe.");

        boolean isOwner = assignment.getAssignedBy().getId().equals(actor.getId());
        if (!isOwner && !actor.canUnassignOthers())
            throw new BusinessException("Solo puede quitar a quienes usted asignó");

        if (assignment.getStaffRole() == StaffRole.COORDINATOR)
            assignmentRepository.deleteByEventAndAssigner(assignment.getEvent().getId(),
                    assignment.getStaff().getId());

        assignmentRepository.delete(idAssignment);
    }

    private Event validateAssignment(Staff actor, Staff target, int eventId) {
        if (actor == null || target == null)
            throw new BusinessException("El personal que asigna y el asignado son obligatorios.");

        Event event = requireEvent(eventId);
        for (AssignmentRule rule : commonRules)
            rule.check(actor, target, event);
        for (AssignmentRule rule : rulesByRole.getOrDefault(target.getStaffRole(), List.of()))
            rule.check(actor, target, event);

        return event;
    }

    private Event requireEvent(int eventId) {
        Event event = eventRepository.getById(eventId);
        if (event == null)
            throw new BusinessException("El evento no existe");

        return event;
    }

}
