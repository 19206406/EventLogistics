package com.desgroup.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.desgroup.interfaces.repositoriesInterfaces.IManagerRepository;
import com.desgroup.models.Manager;

public class ManagerRepository implements IManagerRepository {

    private final SessionFactory sessionFactory;

    public ManagerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Manager getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Manager.class, id);
        }
    }
}
