package com.desgroup.interfaces.repositoriesInterfaces;

import java.util.List;

import com.desgroup.models.Coordinator;

public interface ICoordinatorRepository {

    List<Coordinator> getAll();

    Coordinator getById(int id);

    Coordinator getByEmail(String email);

    void create(Coordinator coordinator);

    void updated(Coordinator coordinator);

    void delete(int id);
}
