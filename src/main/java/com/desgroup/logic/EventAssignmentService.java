package com.desgroup.logic;

import java.util.List;

import com.desgroup.interfaces.logicInterfaces.IEventAssignmentService;
import com.desgroup.interfaces.repositoriesInterfaces.ICoordinatorRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IEventRepository;
import com.desgroup.interfaces.repositoriesInterfaces.ILogisticRepository;
import com.desgroup.models.Coordinator;
import com.desgroup.models.Event;
import com.desgroup.models.EventAssignment;
import com.desgroup.models.Logistic;
import com.desgroup.models.Manager;
import com.desgroup.models.Staff;
import com.desgroup.utils.BusinessException;
import com.desgroup.utils.StaffRole;

public class EventAssignmentService implements IEventAssignmentService {

    private final IEventAssignmentRepository assignmentRepository;
    private final IEventRepository eventRepository;
    private final ILogisticRepository logisticRepository;
    private final ICoordinatorRepository coordinatorRepository;

    public EventAssignmentService(IEventAssignmentRepository assignmentRepository, IEventRepository eventRepository,
            ILogisticRepository logisticRepository, ICoordinatorRepository coordinatorRepository) {
        this.assignmentRepository = assignmentRepository;
        this.eventRepository = eventRepository;
        this.logisticRepository = logisticRepository;
        this.coordinatorRepository = coordinatorRepository;
    }

    public List<EventAssignment> getAllAssignment() {
        return assignmentRepository.getAll();
    }

    public EventAssignment getAssignmentById(int id) {
        return assignmentRepository.getById(id);
    }

    public void createAssignment(Staff staff, Event event, Staff assignedBy, StaffRole staffRole) {
        if (staff == null || event == null || assignedBy == null || staffRole == null)
            throw new BusinessException("Los datos de la asignación están incompletos.");

        if (assignmentRepository.exists(staff.getId(), event.getId()))
            throw new BusinessException("La persona ya está asignada al evento");

        assignmentRepository.create(new EventAssignment(staff, event, assignedBy, staffRole));
    }

    public void deleteAssignment(int id) {
        assignmentRepository.delete(id);
    }

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
    public List<Logistic> getAvailableLogistics(int eventId) {
        Event event = requireEvent(eventId);
        return logisticRepository.getAll().stream()
                .filter(l -> !assignmentRepository.exists(l.getId(), eventId))
                .filter(l -> !hasScheduleConflict(l.getId(), event))
                .toList();
    }

    @Override
    public List<Coordinator> getAvailableCoordinators(int eventId) {
        Event event = requireEvent(eventId);
        return coordinatorRepository.getAll().stream()
                .filter(c -> !assignmentRepository.exists(c.getId(), eventId))
                .filter(c -> !hasScheduleConflict(c.getId(), event))
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

        StaffRole role;
        if (target instanceof Coordinator)
            role = StaffRole.COORDINATOR;
        else if (target instanceof Logistic)
            role = StaffRole.LOGISTIC;
        else
            throw new BusinessException("El tipo de personal no puede asignarse a un evento.");

        assignmentRepository.create(new EventAssignment(target, event, actor, role));
    }

    /**
     * Comprueba todas las reglas de negocio para una asignación.
     * Si alguna falla lanza {@link BusinessException}; si todo está correcto
     * devuelve el evento para no volver a consultarlo.
     */
    private Event validateAssignment(Staff actor, Staff target, int eventId) {
        if (actor == null || target == null)
            throw new BusinessException("El personal que asigna y el asignado son obligatorios.");

        Event event = requireEvent(eventId);

        if (isClosed(event))
            throw new BusinessException("El evento está finalizado o cancelado");
        if (!actor.canAssign(target))
            throw new BusinessException("Usted no tiene permiso para asignar a esta persona");
        if (assignmentRepository.exists(target.getId(), eventId))
            throw new BusinessException("Esta persona ya está asignada al evento.");
        if (hasScheduleConflict(target.getId(), event))
            throw new BusinessException("Esta persona tiene otro evento que se cruza");

        if (target instanceof Coordinator) {
            if (assignmentRepository.countByEventAndRole(eventId, StaffRole.COORDINATOR) >= event.getMaxCoordinators())
                throw new BusinessException("El evento alcanzó el máximo de coordinadores");
        } else if (target instanceof Logistic) {
            if (!assignmentRepository.exists(actor.getId(), eventId))
                throw new BusinessException("Usted no está asignado a este evento");
            if (assignmentRepository.countByEventAndRole(eventId, StaffRole.LOGISTIC) >= event.getMaxLogistics())
                throw new BusinessException("El evento alcanzó el máximo de logísticos");
            if (assignmentRepository.countByEventAndAssigner(eventId, actor.getId()) >= event
                    .getMaxLogisticsPerCoordinator())
                throw new BusinessException("Alcanzó su máximo de logísticos para este evento.");
        } else {
            throw new BusinessException("El tipo de personal no puede asignarse a un evento.");
        }

        return event;
    }

    @Override
    public void unassign(Staff actor, int idAssignment) {
        EventAssignment assignment = assignmentRepository.getById(idAssignment);
        if (assignment == null)
            throw new BusinessException("La asignación no existe.");

        boolean isOwner = assignment.getAssignedBy().getId().equals(actor.getId());
        if (!isOwner && !(actor instanceof Manager))
            throw new BusinessException("Solo puede quitar a quienes usted asignó");

        if (assignment.getStaffRole() == StaffRole.COORDINATOR)
            assignmentRepository.deleteByEventAndAssigner(assignment.getEvent().getId(), assignment.getStaff().getId());

        assignmentRepository.delete(idAssignment);
    }

    private Event requireEvent(int eventId) {
        Event event = eventRepository.getById(eventId);
        if (event == null)
            throw new BusinessException("The event does not exist.");

        return event;
    }

    private boolean isClosed(Event e) {
        return "Finalizado".equals(e.getState()) || "Cancelado".equals(e.getState());
    }

    private boolean hasScheduleConflict(int staffId, Event newEvent) {
        for (EventAssignment a : assignmentRepository.getAllByStaff(staffId)) {
            Event other = eventRepository.getById(a.getEvent().getId());
            if (other != null && !isClosed(other) && overlaps(other, newEvent))
                return true;
        }
        return false;
    }

    private boolean overlaps(Event a, Event b) {
        return a.getDate().equals(b.getDate())
                && a.getStartTime() < b.getEndTime()
                && b.getStartTime() < a.getEndTime();
    }

}
