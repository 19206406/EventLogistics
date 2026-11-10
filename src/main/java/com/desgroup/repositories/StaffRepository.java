package com.desgroup.repositories;

import java.util.List;

import org.hibernate.SessionFactory;

import com.desgroup.interfaces.repositoriesInterfaces.IStaffRepository;
import com.desgroup.models.Staff;

public class StaffRepository implements IStaffRepository {

    private final HibernateExecutor hibernate;

    public StaffRepository(SessionFactory sessionFactory) {
        this.hibernate = new HibernateExecutor(sessionFactory);
    }

    @Override
    public List<Staff> getAll() {
        return hibernate.read(session -> session.createQuery("FROM Staff", Staff.class).list());
    }

    @Override
    public Staff getById(int id) {
        return hibernate.read(session -> session.get(Staff.class, id));
    }

    @Override
    public Staff getByEmail(String email) {
        return hibernate.read(session -> session.createQuery(
                "FROM Staff s WHERE s.email = :email", Staff.class)
                .setParameter("email", email)
                .uniqueResult());
    }
}
