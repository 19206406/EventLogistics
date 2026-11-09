package com.desgroup.repositories;

import org.hibernate.SessionFactory;

import com.desgroup.interfaces.repositoriesInterfaces.IManagerRepository;
import com.desgroup.models.Manager;

public class ManagerRepository implements IManagerRepository {

    private final HibernateExecutor hibernate;

    public ManagerRepository(SessionFactory sessionFactory) {
        this.hibernate = new HibernateExecutor(sessionFactory);
    }

    @Override
    public Manager getById(int id) {
        return hibernate.read(session -> session.get(Manager.class, id));
    }
}
