package com.desgroup.repositories;

import java.util.List;

import org.hibernate.SessionFactory;

import com.desgroup.interfaces.repositoriesInterfaces.ICoordinatorRepository;
import com.desgroup.models.Coordinator;

public class CoordinatorRepository implements ICoordinatorRepository {

    private final HibernateExecutor hibernate;

    public CoordinatorRepository(SessionFactory sessionFactory) {
        this.hibernate = new HibernateExecutor(sessionFactory);
    }

    @Override
    public List<Coordinator> getAll() {
        return hibernate.read(session -> session.createQuery("FROM Coordinator", Coordinator.class).list());
    }

    @Override
    public Coordinator getById(int id) {
        return hibernate.read(session -> session.get(Coordinator.class, id));
    }

    @Override
    public Coordinator getByEmail(String email) {
        return hibernate.read(session -> session.createQuery(
                        "FROM Coordinator s WHERE s.email = :email", Coordinator.class)
                .setParameter("email", email)
                .uniqueResult());
    }

    @Override
    public void create(Coordinator coordinator) {
        hibernate.write(session -> session.persist(coordinator));
    }

    @Override
    public void updated(Coordinator coordinator) {
        hibernate.write(session -> session.merge(coordinator));
    }

    @Override
    public void deleteWithAssignments(int id) {
        hibernate.write(session -> {
            session.createMutationQuery("DELETE FROM EventAssignment a "
                    + "WHERE a.assignedBy.id = :id OR a.staff.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();

            Coordinator coordinator = session.get(Coordinator.class, id);
            if (coordinator != null)
                session.remove(coordinator);
        });
    }
}
