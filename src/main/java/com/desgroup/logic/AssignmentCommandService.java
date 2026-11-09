package com.desgroup.logic;

import java.util.List;
import java.util.Map;

import com.desgroup.exceptions.BusinessException;
import com.desgroup.interfaces.logicInterfaces.IAssignmentCommandService;
import com.desgroup.interfaces.repositoriesInterfaces.IAssignmentReader;
import com.desgroup.interfaces.repositoriesInterfaces.IAssignmentWriter;
import com.desgroup.interfaces.repositoriesInterfaces.IEventRepository;
import com.desgroup.interfaces.rulesInterfaces.AssignmentRule;
import com.desgroup.models.Event;
import com.desgroup.models.EventAssignment;
import com.desgroup.models.Staff;
import com.desgroup.utils.StaffRole;

public class AssignmentCommandService implements IAssignmentCommandService {

    private final IAssignmentReader assignmentReader;
    private final IAssignmentWriter assignmentWriter;
    private final IEventRepository eventRepository;
    private final List<AssignmentRule> commonRules;
    private final Map<StaffRole, List<AssignmentRule>> rulesByRole;

    public AssignmentCommandService(IAssignmentReader assignmentReader, IAssignmentWriter assignmentWriter,
            IEventRepository eventRepository, List<AssignmentRule> commonRules,
            Map<StaffRole, List<AssignmentRule>> rulesByRole) {
        this.assignmentReader = assignmentReader;
        this.assignmentWriter = assignmentWriter;
        this.eventRepository = eventRepository;
        this.commonRules = commonRules;
        this.rulesByRole = rulesByRole;
    }

    @Override
    public void assign(Staff actor, Staff target, int eventId) {
        if (actor == null || target == null)
            throw new BusinessException("El personal que asigna y el asignado son obligatorios.");

        assignmentWriter.createExclusive(eventId, target.getId(), () -> {
            Event event = validateAssignment(actor, target, eventId);
            return new EventAssignment(target, event, actor, target.getStaffRole());
        });
    }

    @Override
    public void unassign(Staff actor, int idAssignment) {
        if (actor == null)
            throw new BusinessException("El personal que desasigna es obligatorio.");

        EventAssignment assignment = assignmentReader.getById(idAssignment);
        if (assignment == null)
            throw new BusinessException("La asignación no existe.");

        boolean isOwner = assignment.getAssignedBy().getId().equals(actor.getId());
        if (!isOwner && !actor.canUnassignOthers())
            throw new BusinessException("Solo puede quitar a quienes usted asignó");

        if (requireEvent(assignment.getEvent().getId()).isClosed())
            throw new BusinessException("No se puede desasignar personal de un evento finalizado o cancelado");

        if (assignment.getStaffRole() == StaffRole.COORDINATOR)
            assignmentWriter.deleteWithDelegated(idAssignment);
        else
            assignmentWriter.delete(idAssignment);
    }

    private Event validateAssignment(Staff actor, Staff target, int eventId) {
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
