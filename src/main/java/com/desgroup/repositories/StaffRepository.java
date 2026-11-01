package com.desgroup.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.desgroup.interfaces.repositoriesInterfaces.IStaffRepository;
import com.desgroup.models.Staff;

public class StaffRepository implements IStaffRepository {

    private final SessionFactory sessionFactory;

    public StaffRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Staff> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Staff", Staff.class).list();
        }
    }

    @Override
    public Staff getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Staff.class, id);
        }
    }

    @Override
    public Staff getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM Staff s WHERE s.email = :email", Staff.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }
}
