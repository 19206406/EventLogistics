package com.desgroup.interfaces.repositoriesInterfaces;

import java.util.List;

import com.desgroup.models.Logistic;

public interface ILogisticRepository {
    List<Logistic> getAll();

    Logistic getById(int id);

    Logistic getByEmail(String email);

    void create(Logistic logistic);

    void updated(Logistic logistic);

    /** Elimina al logístico y sus asignaciones en una sola transacción. */
    void deleteWithAssignments(int id);
}
