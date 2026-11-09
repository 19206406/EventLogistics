package com.desgroup.interfaces.repositoriesInterfaces;

import java.util.List;

import com.desgroup.models.Event;

public interface IEventRepository {
    List<Event> getAll();

    Event getById(int id);

    void create(Event event);

    void updated(Event event);

    /** Elimina el evento y sus asignaciones en una sola transacción. */
    void deleteWithAssignments(int id);
}
