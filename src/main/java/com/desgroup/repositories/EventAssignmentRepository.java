package com.desgroup.repositories;

import java.util.List;
import java.util.function.Supplier;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.desgroup.exceptions.BusinessException;
import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.models.Event;
import com.desgroup.models.EventAssignment;
import com.desgroup.models.Staff;
import com.desgroup.utils.StaffRole;

public class EventAssignmentRepository implements IEventAssignmentRepository {

    private static final Class<EventAssignment> ENTITY = EventAssignment.class;

    private final HibernateExecutor hibernate;

    public EventAssignmentRepository(SessionFactory sessionFactory) {
        this.hibernate = new HibernateExecutor(sessionFactory);
    }

    @Override
    public EventAssignment getById(int id) {
        return hibernate.read(session -> {
            EventAssignment assignment = session.get(ENTITY, id);
            return assignment == null ? null : toDomain(assignment);
        });
    }

    @Override
    public List<EventAssignment> getAllByStaff(int staffId) {
        return hibernate.read(session -> session
                .createQuery("FROM EventAssignment a WHERE a.staff.id = :staffId", ENTITY)
                .setParameter("staffId", staffId)
                .list()
                .stream()
                .map(EventAssignmentRepository::toDomain)
                .toList());
    }

    @Override
    public List<EventAssignment> getAllByEvent(int eventId) {
        return hibernate.read(session -> session
                .createQuery("FROM EventAssignment a WHERE a.event.id = :eventId", ENTITY)
                .setParameter("eventId", eventId)
                .list()
                .stream()
                .map(EventAssignmentRepository::toDomain)
                .toList());
    }

    @Override
    public int countByEventAndRole(int eventId, StaffRole role) {
        return hibernate.read(session -> session
                .createQuery("SELECT COUNT(a) FROM EventAssignment a "
                        + "WHERE a.event.id = :eventId AND a.staffRole = :role", Long.class)
                .setParameter("eventId", eventId)
                .setParameter("role", role)
                .getSingleResult()
                .intValue());
    }

    @Override
    public int countByEventAndAssigner(int eventId, int assignerId) {
        return hibernate.read(session -> session
                .createQuery("SELECT COUNT(a) FROM EventAssignment a "
                        + "WHERE a.event.id = :eventId AND a.assignedBy.id = :assignerId", Long.class)
                .setParameter("eventId", eventId)
                .setParameter("assignerId", assignerId)
                .getSingleResult()
                .intValue());
    }

    @Override
    public boolean exists(int staffId, int eventId) {
        return hibernate.read(session -> session
                .createQuery("SELECT COUNT(a) FROM EventAssignment a "
                        + "WHERE a.staff.id = :staffId AND a.event.id = :eventId", Long.class)
                .setParameter("staffId", staffId)
                .setParameter("eventId", eventId)
                .getSingleResult() > 0);
    }

    @Override
    public void createExclusive(int eventId, int staffId, Supplier<EventAssignment> validatedAssignment) {
        hibernate.write(session -> {
            lockRow(session, "event", eventId, "El evento no existe.");
            lockRow(session, "staff", staffId, "El miembro del personal no existe.");

            EventAssignment assignment = validatedAssignment.get();

            session.persist(new EventAssignment(
                    requireStaff(session, assignment.getStaff().getId()),
                    requireEvent(session, assignment.getEvent().getId()),
                    requireStaff(session, assignment.getAssignedBy().getId()),
                    assignment.getStaffRole()));
        });
    }

    @Override
    public void delete(int id) {
        hibernate.write(session -> {
            EventAssignment assignment = session.get(ENTITY, id);
            if (assignment != null)
                session.remove(assignment);
        });
    }

    @Override
    public void deleteWithDelegated(int id) {
        hibernate.write(session -> {
            EventAssignment assignment = session.get(ENTITY, id);
            if (assignment == null)
                return;

            session.createMutationQuery("DELETE FROM EventAssignment a "
                    + "WHERE a.event.id = :eventId AND a.assignedBy.id = :assignerId")
                    .setParameter("eventId", assignment.getEvent().getId())
                    .setParameter("assignerId", assignment.getStaff().getId())
                    .executeUpdate();
            session.remove(assignment);
        });
    }

    private static EventAssignment toDomain(EventAssignment assignment) {
        return new EventAssignment(
                assignment.getId(),
                assignment.getStaff(),
                assignment.getEvent(),
                assignment.getAssignedBy(),
                assignment.getStaffRole());
    }

    private static void lockRow(Session session, String table, int id, String notFoundMessage) {
        Integer lockedId = session
                .createNativeQuery("SELECT id FROM " + table + " WHERE id = :id FOR UPDATE", Integer.class)
                .setParameter("id", id)
                .uniqueResult();
        if (lockedId == null)
            throw new BusinessException(notFoundMessage);
    }

    private static Staff requireStaff(Session session, int staffId) {
        Staff staff = session.get(Staff.class, staffId);
        if (staff == null)
            throw new BusinessException("El miembro del personal no existe.");
        return staff;
    }

    private static Event requireEvent(Session session, int eventId) {
        Event event = session.get(Event.class, eventId);
        if (event == null)
            throw new BusinessException("El evento no existe.");
        return event;
    }
}
