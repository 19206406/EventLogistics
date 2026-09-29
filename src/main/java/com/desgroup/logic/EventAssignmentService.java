package com.desgroup.logic;

import java.util.List;

import com.desgroup.interfaces.logicInterfaces.IEventAssignmentService;
import com.desgroup.interfaces.repositoriesInterfaces.ICoordinatorRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IEventRepository;
import com.desgroup.interfaces.repositoriesInterfaces.ILogisticRepository;
import com.desgroup.models.*;
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

    public void createAssignment(int idStaff, int idEvent) {
        if (assignmentRepository.exists(idStaff, idEvent))
            throw new BusinessException("La persona ya esta asignada al evento");

        EventAssignment newAssignment = new EventAssignment(0, idStaff, idEvent);
        assignmentRepository.create(newAssignment);
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
                .filter(a -> a.getIdAssignedBy() == coordinatorId && a.getStaffRole() == StaffRole.LOGISTIC)
                .toList();
    }

    @Override
    public List<Logistic> getAvailableLogistics(int eventId) {
        Event event = requireEvent(eventId);
        return logisticRepository.getAll().stream()
                .filter(l -> !assignmentRepository.exists(l.getIdStaff(), eventId))
                .filter(l -> !hasScheduleConflict(l.getIdStaff(), event))
                .toList();
    }

    @Override
    public List<Coordinator> getAvailableCoordinators(int eventId) {
        Event event = requireEvent(eventId);
        return coordinatorRepository.getAll().stream()
                .filter(c -> !assignmentRepository.exists(c.getIdStaff(), eventId))
                .filter(c -> !hasScheduleConflict(c.getIdStaff(), event))
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
    public void assign(Staff actor, Staff target, int eventId) {
        Event event = requireEvent(eventId);

        if (isClosed(event))
            throw new BusinessException("El evento está finalizado o cancelado");
        if (!actor.canAssign(target))
            throw new BusinessException("Usted no tiene permiso para asinar a esta persona");
        if (assignmentRepository.exists(target.getIdStaff(), eventId))
            throw new BusinessException("Esta persona ya está asignada al evento.");
        if (hasScheduleConflict(target.getIdStaff(), event))
            throw new BusinessException("Esta persona tiene otro evento que se cruza");


        if (target instanceof Coordinator) {
            if (assignmentRepository.countByEventAndRole(eventId, StaffRole.COORDINATOR) >= event.getMaxCoordinators())
                throw new BusinessException("El evento alcanzó el máximo de coordinadores");
            save(actor, target, eventId, StaffRole.COORDINATOR);
        } else if (target instanceof Logistic) {
            if (!assignmentRepository.exists(actor.getIdStaff(), eventId))
                throw new BusinessException("Usted no está asignado a este evento");
            if (assignmentRepository.countByEventAndRole(eventId, StaffRole.LOGISTIC) >= event.getMaxLogistics())
                throw new BusinessException("El evento alcanzó el máximo de logísticos");
            if (assignmentRepository.countByEventAndAssigner(eventId, actor.getIdStaff()) >= event.getMaxLogisticsPerCoordinator())
                throw new BusinessException("Alcanzó su máximo de logísticos para este evento.");
            save(actor, target, eventId, StaffRole.LOGISTIC);
        }
    }

    private void save(Staff actor, Staff target, int eventId, StaffRole role) {
        assignmentRepository.create(new EventAssignment(0, target.getIdStaff(), eventId, actor.getIdStaff(), role));
    }

    @Override
    public void unassign(Staff actor, int idAssignment) {
        EventAssignment assignment = assignmentRepository.getById(idAssignment);
        if (assignment == null)
            throw new BusinessException("La asignación no existe.");

        boolean isOwner = assignment.getIdAssignedBy() == actor.getIdStaff();
        if (!isOwner && !(actor instanceof Manager))
            throw new BusinessException("Solo puede quitar a quienes usted asignó");

        if (assignment.getStaffRole() == StaffRole.COORDINATOR)
            assignmentRepository.deleteByEventAndAssigner(assignment.getIdEvent(), assignment.getIdStaff());

        assignmentRepository.delete(idAssignment);
    }

    private Event requireEvent(int eventId) {
        Event event = eventRepository.getById(eventId);
        if (event == null)
            return null;

        return event;
    }

    private boolean isClosed(Event e) {
        return "Finalizado".equals(e.getState()) || "Cancelado".equals(e.getState());
    }

    private boolean hasScheduleConflict(int staffId, Event newEvent) {
        for (EventAssignment a : assignmentRepository.getAllByStaff(staffId)) {
            Event other = eventRepository.getById(a.getIdEvent());
            if (other != null && !isClosed(other) && overlaps(other, newEvent)) return true;
        }
        return false;
    }

    private boolean overlaps(Event a, Event b) {
        return a.getDate().equals(b.getDate())
                && a.getStartTime() < b.getEndTime()
                && b.getStartTime() < a.getEndTime();
    }

}
