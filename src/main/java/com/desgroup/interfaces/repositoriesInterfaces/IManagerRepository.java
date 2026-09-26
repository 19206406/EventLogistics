package com.desgroup.interfaces.repositoriesInterfaces;

import java.util.List;

import com.desgroup.models.Manager;

public interface IManagerRepository {
    List<Manager> getAll();

    Manager getById(int id);

    Manager getByEmail(String email);

    void create(Manager manager);

    void updated(Manager manager);

    void delete(int id);
}
