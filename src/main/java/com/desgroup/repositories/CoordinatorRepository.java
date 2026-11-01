package com.desgroup.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.desgroup.interfaces.repositoriesInterfaces.ICoordinatorRepository;
import com.desgroup.models.Coordinator;

public class CoordinatorRepository implements ICoordinatorRepository {

    private final SessionFactory sessionFactory;

    public CoordinatorRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Coordinator> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Coordinator", Coordinator.class).list();
        }
    }

    @Override
    public Coordinator getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Coordinator.class, id);
        }
    }

    @Override
    public Coordinator getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM Coordinator s WHERE s.email = :email", Coordinator.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }

    @Override
    public void create(Coordinator coordinator) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(coordinator);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public void updated(Coordinator coordinator) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.merge(coordinator);
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
                Coordinator course = session.get(Coordinator.class, id);
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
