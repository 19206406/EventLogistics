package com.desgroup.logic;

import java.util.List;

import com.desgroup.exceptions.BusinessException;
import com.desgroup.interfaces.logicInterfaces.IAssignmentQueryService;
import com.desgroup.interfaces.repositoriesInterfaces.IAssignmentReader;
import com.desgroup.models.EventAssignment;
import com.desgroup.utils.StaffRole;

public class AssignmentQueryService implements IAssignmentQueryService {

    private final IAssignmentReader eventAssignmentRepository;

    public AssignmentQueryService(IAssignmentReader eventAssignmentRepository) {
        this.eventAssignmentRepository = eventAssignmentRepository;
    }

    @Override
    public EventAssignment getAssignmentById(int id) {
        EventAssignment assignment = eventAssignmentRepository.getById(id);
        if (assignment == null)
            throw new BusinessException("La asignación no existe.");

        return assignment;
    }

    @Override
    public List<EventAssignment> getAssignmentsByStaffId(int staffId) {
        return eventAssignmentRepository.getAllByStaff(staffId);
    }

    @Override
    public List<EventAssignment> getAssignmentsByEvent(int eventId) {
        return eventAssignmentRepository.getAllByEvent(eventId);
    }

    @Override
    public List<EventAssignment> getLogisticsAssignedBy(int eventId, int coordinatorId) {
        return eventAssignmentRepository.getAllByEvent(eventId).stream()
                .filter(a -> a.getAssignedBy().getId().equals(coordinatorId)
                        && a.getStaffRole() == StaffRole.LOGISTIC)
                .toList();
    }

    @Override
    public List<EventAssignment> getAssignmentsByEventAndRole(int eventId, StaffRole role) {
        return eventAssignmentRepository.getAllByEvent(eventId).stream()
                .filter(a -> a.getStaffRole() == role)
                .toList();
    }

}
