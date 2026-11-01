package com.desgroup.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.desgroup.interfaces.repositoriesInterfaces.IEventRepository;
import com.desgroup.models.Event;

public class EventRepository implements IEventRepository {

    private final SessionFactory sessionFactory;

    public EventRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Event> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT e FROM Event e JOIN FETCH e.place", Event.class)
                    .list();
        }
    }

    @Override
    public Event getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT e FROM Event e JOIN FETCH e.place WHERE e.id = :id", Event.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    @Override
    public void create(Event event) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(event);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public void updated(Event event) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.merge(event);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Event course = session.get(Event.class, id);
                if (course != null)
                    session.remove(course);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

}
