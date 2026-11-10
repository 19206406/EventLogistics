package com.desgroup.repositories;

import java.util.List;

import org.hibernate.SessionFactory;

import com.desgroup.interfaces.repositoriesInterfaces.ILogisticRepository;
import com.desgroup.models.Logistic;

public class LogisticRepository implements ILogisticRepository {

    private final HibernateExecutor hibernate;

    public LogisticRepository(SessionFactory sessionFactory) {
        this.hibernate = new HibernateExecutor(sessionFactory);
    }

    @Override
    public List<Logistic> getAll() {
        return hibernate.read(session -> session.createQuery("FROM Logistic", Logistic.class).list());
    }

    @Override
    public Logistic getById(int id) {
        return hibernate.read(session -> session.get(Logistic.class, id));
    }

    @Override
    public Logistic getByEmail(String email) {
        return hibernate.read(session -> session.createQuery(
                "FROM Logistic s WHERE s.email = :email", Logistic.class)
                .setParameter("email", email)
                .uniqueResult());
    }

    @Override
    public void create(Logistic logistic) {
        hibernate.write(session -> session.persist(logistic));
    }

    @Override
    public void updated(Logistic logistic) {
        hibernate.write(session -> session.merge(logistic));
    }

    @Override
    public void deleteWithAssignments(int id) {
        hibernate.write(session -> {
            session.createMutationQuery("DELETE FROM EventAssignment a WHERE a.staff.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();

            Logistic logistic = session.get(Logistic.class, id);
            if (logistic != null)
                session.remove(logistic);
        });
    }
}
