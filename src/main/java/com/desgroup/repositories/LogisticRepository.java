package com.desgroup.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.desgroup.interfaces.repositoriesInterfaces.ILogisticRepository;
import com.desgroup.models.Logistic;
import com.desgroup.utils.HibernateUtil;

public class LogisticRepository implements ILogisticRepository {

    @Override
    public List<Logistic> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Logistic", Logistic.class).list();
        }
    }

    @Override
    public Logistic getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Logistic.class, id);
        }
    }

    @Override
    public Logistic getByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Logistic s WHERE s.email = :email", Logistic.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }

    @Override
    public void create(Logistic logistic) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(logistic);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public void updated(Logistic logistic) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.merge(logistic);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Logistic course = session.get(Logistic.class, id);
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
