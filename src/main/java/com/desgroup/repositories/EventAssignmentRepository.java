package com.desgroup.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.models.Event;
import com.desgroup.models.EventAssignment;
import com.desgroup.models.Staff;
import com.desgroup.utils.HibernateUtil;
import com.desgroup.utils.StaffRole;

public class EventAssignmentRepository implements IEventAssignmentRepository {

    private static final Class<com.desgroup.models.EventAssignment> ENTITY = com.desgroup.models.EventAssignment.class;

    @Override
    public List<EventAssignment> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM EventAssignment", ENTITY).list()
                    .stream()
                    .map(EventAssignmentRepository::toDomain)
                    .toList();
        }
    }

    @Override
    public EventAssignment getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            com.desgroup.models.EventAssignment assignment = session.get(ENTITY, id);
            return assignment == null ? null : toDomain(assignment);
        }
    }

    @Override
    public List<EventAssignment> getAllByStaff(int staffId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session
                    .createQuery("FROM EventAssignment a WHERE a.staff.id = :staffId", ENTITY)
                    .setParameter("staffId", staffId)
                    .list()
                    .stream()
                    .map(EventAssignmentRepository::toDomain)
                    .toList();
        }
    }

    @Override
    public List<EventAssignment> getAllByEvent(int eventId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session
                    .createQuery("FROM EventAssignment a WHERE a.event.id = :eventId", ENTITY)
                    .setParameter("eventId", eventId)
                    .list()
                    .stream()
                    .map(EventAssignmentRepository::toDomain)
                    .toList();
        }
    }

    @Override
    public int countByEventAndRole(int eventId, StaffRole role) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session
                    .createQuery("SELECT COUNT(a) FROM EventAssignment a "
                            + "WHERE a.event.id = :eventId AND a.staffRole = :role", Long.class)
                    .setParameter("eventId", eventId)
                    .setParameter("role", role)
                    .getSingleResult();
            return count.intValue();
        }
    }

    @Override
    public int countByEventAndAssigner(int eventId, int assignerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session
                    .createQuery("SELECT COUNT(a) FROM EventAssignment a "
                            + "WHERE a.event.id = :eventId AND a.assignedBy.id = :assignerId", Long.class)
                    .setParameter("eventId", eventId)
                    .setParameter("assignerId", assignerId)
                    .getSingleResult();
            return count.intValue();
        }
    }

    @Override
    public void create(EventAssignment assignment) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                EventAssignment entity = new com.desgroup.models.EventAssignment(
                        requireStaff(session, assignment.getStaff().getId()),
                        requireEvent(session, assignment.getEvent().getId()),
                        requireStaff(session, assignment.getAssignedBy().getId()),
                        assignment.getStaffRole());

                session.persist(entity);
                tx.commit();

                assignment.setId(entity.getId());
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public void updated(EventAssignment assignment) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                com.desgroup.models.EventAssignment entity = session.get(ENTITY, assignment.getId());
                if (entity != null) {
                    entity.setStaff(requireStaff(session, assignment.getStaff().getId()));
                    entity.setEvent(requireEvent(session, assignment.getEvent().getId()));
                    entity.setAssignedBy(requireStaff(session, assignment.getAssignedBy().getId()));
                    entity.setStaffRole(assignment.getStaffRole());
                }
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public boolean exists(int staffId, int eventId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session
                    .createQuery("SELECT COUNT(a) FROM EventAssignment a "
                            + "WHERE a.staff.id = :staffId AND a.event.id = :eventId", Long.class)
                    .setParameter("staffId", staffId)
                    .setParameter("eventId", eventId)
                    .getSingleResult();
            return count > 0;
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                com.desgroup.models.EventAssignment assignment = session.get(ENTITY, id);
                if (assignment != null) {
                    session.remove(assignment);
                }
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public void deleteByEvent(int eventId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.createMutationQuery("DELETE FROM EventAssignment a WHERE a.event.id = :eventId")
                        .setParameter("eventId", eventId)
                        .executeUpdate();
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public void deleteByStaff(int staffId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.createMutationQuery("DELETE FROM EventAssignment a WHERE a.staff.id = :staffId")
                        .setParameter("staffId", staffId)
                        .executeUpdate();
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public void deleteByAssigner(int assignerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.createMutationQuery("DELETE FROM EventAssignment a WHERE a.assignedBy.id = :assignerId")
                        .setParameter("assignerId", assignerId)
                        .executeUpdate();
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public void deleteByEventAndAssigner(int eventId, int assignerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.createMutationQuery("DELETE FROM EventAssignment a "
                        + "WHERE a.event.id = :eventId AND a.assignedBy.id = :assignerId")
                        .setParameter("eventId", eventId)
                        .setParameter("assignerId", assignerId)
                        .executeUpdate();
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    private static EventAssignment toDomain(com.desgroup.models.EventAssignment assignment) {
        return new EventAssignment(
                assignment.getId(),
                assignment.getStaff(),
                assignment.getEvent(),
                assignment.getAssignedBy(),
                assignment.getStaffRole());
    }

    private static Staff requireStaff(Session session, int staffId) {
        Staff staff = session.get(Staff.class, staffId);
        if (staff == null) {
            throw new IllegalArgumentException("No existe el miembro del personal con id " + staffId);
        }
        return staff;
    }

    private static Event requireEvent(Session session, int eventId) {
        Event event = session.get(Event.class, eventId);
        if (event == null) {
            throw new IllegalArgumentException("No existe el evento con id " + eventId);
        }
        return event;
    }
}
