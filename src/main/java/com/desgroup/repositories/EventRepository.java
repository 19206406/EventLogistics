package com.desgroup.repositories;

import java.util.List;

import org.hibernate.SessionFactory;

import com.desgroup.interfaces.repositoriesInterfaces.IEventRepository;
import com.desgroup.models.Event;

public class EventRepository implements IEventRepository {

    private final HibernateExecutor hibernate;

    public EventRepository(SessionFactory sessionFactory) {
        this.hibernate = new HibernateExecutor(sessionFactory);
    }

    @Override
    public List<Event> getAll() {
        return hibernate.read(session -> session.createQuery(
                "SELECT e FROM Event e JOIN FETCH e.place", Event.class)
                .list());
    }

    @Override
    public Event getById(int id) {
        return hibernate.read(session -> session.createQuery(
                "SELECT e FROM Event e JOIN FETCH e.place WHERE e.id = :id", Event.class)
                .setParameter("id", id)
                .uniqueResult());
    }

    @Override
    public void create(Event event) {
        hibernate.write(session -> session.persist(event));
    }

    @Override
    public void updated(Event event) {
        hibernate.write(session -> session.merge(event));
    }

    @Override
    public void deleteWithAssignments(int id) {
        hibernate.write(session -> {
            session.createMutationQuery("DELETE FROM EventAssignment a WHERE a.event.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();

            Event event = session.get(Event.class, id);
            if (event != null)
                session.remove(event);
        });
    }
}
