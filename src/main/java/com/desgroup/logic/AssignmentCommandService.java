package com.desgroup.logic;

import java.util.List;
import java.util.Map;

import com.desgroup.interfaces.logicInterfaces.IAssignmentCommandService;
import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IEventRepository;
import com.desgroup.interfaces.rulesInterfaces.AssignmentRule;
import com.desgroup.models.Event;
import com.desgroup.models.EventAssignment;
import com.desgroup.models.Staff;
import com.desgroup.utils.BusinessException;
import com.desgroup.utils.StaffRole;

public class AssignmentCommandService implements IAssignmentCommandService {

    private final IEventAssignmentRepository eventAssignmentRepository;
    private final IEventRepository eventRepository;
    private final List<AssignmentRule> commonRules;
    private final Map<StaffRole, List<AssignmentRule>> rulesByRole;

    public AssignmentCommandService(IEventAssignmentRepository eventAssignmentRepository,
            IEventRepository eventRepository, List<AssignmentRule> commonRules,
            Map<StaffRole, List<AssignmentRule>> rulesByRole) {
        this.eventAssignmentRepository = eventAssignmentRepository;
        this.eventRepository = eventRepository;
        this.commonRules = commonRules;
        this.rulesByRole = rulesByRole;
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
        eventAssignmentRepository.create(new EventAssignment(target, event, actor, target.getStaffRole()));
    }

    @Override
    public void unassign(Staff actor, int idAssignment) {
        EventAssignment assignment = eventAssignmentRepository.getById(idAssignment);
        if (assignment == null)
            throw new BusinessException("La asignación no existe.");

        boolean isOwner = assignment.getAssignedBy().getId().equals(actor.getId());
        if (!isOwner && !actor.canUnassignOthers())
            throw new BusinessException("Solo puede quitar a quienes usted asignó");

        if (assignment.getStaffRole() == StaffRole.COORDINATOR)
            eventAssignmentRepository.deleteByEventAndAssigner(assignment.getEvent().getId(),
                    assignment.getStaff().getId());

        eventAssignmentRepository.delete(idAssignment);
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
