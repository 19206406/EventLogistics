package com.desgroup.repositories;

import java.util.List;

import org.hibernate.Session;

import com.desgroup.interfaces.repositoriesInterfaces.IStaffRepository;
import com.desgroup.models.Staff;
import com.desgroup.utils.HibernateUtil;

public class StaffRepository implements IStaffRepository {

    @Override
    public List<Staff> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM staff", Staff.class).list();
        }
    }

    @Override
    public Staff getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Staff.class, id);
        }
    }

    @Override
    public Staff getByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Staff.class, email);
        }
    }
}
