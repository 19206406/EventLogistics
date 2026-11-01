package com.desgroup.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.desgroup.interfaces.repositoriesInterfaces.IManagerRepository;
import com.desgroup.models.Manager;

public class ManagerRepository implements IManagerRepository {

    private final SessionFactory sessionFactory;

    public ManagerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Manager> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Manager", Manager.class).list();
        }
    }

    @Override
    public Manager getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Manager.class, id);
        }
    }

    @Override
    public Manager getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM Manager s WHERE s.email = :email", Manager.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }

    @Override
    public void create(Manager manager) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(manager);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public void updated(Manager manager) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.merge(manager);
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
                Manager course = session.get(Manager.class, id);
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
