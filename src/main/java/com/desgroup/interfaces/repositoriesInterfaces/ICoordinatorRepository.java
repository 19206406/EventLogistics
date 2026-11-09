package com.desgroup.interfaces.repositoriesInterfaces;

import java.util.List;

import com.desgroup.models.Coordinator;

public interface ICoordinatorRepository {

    List<Coordinator> getAll();

    Coordinator getById(int id);

    Coordinator getByEmail(String email);

    void create(Coordinator coordinator);

    void updated(Coordinator coordinator);

    /** Elimina al coordinador y todas sus asignaciones en una sola transacción. */
    void deleteWithAssignments(int id);
}
