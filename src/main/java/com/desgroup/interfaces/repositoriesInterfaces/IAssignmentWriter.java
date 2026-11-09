package com.desgroup.interfaces.repositoriesInterfaces;

import java.util.function.Supplier;

import com.desgroup.models.EventAssignment;

public interface IAssignmentWriter {
    /**
     * Crea una asignación con el evento y la persona bloqueados, de modo que
     * otra asignación simultánea no invalide lo que validatedAssignment comprobó.
     * Si validatedAssignment lanza una excepción, no se crea nada.
     */
    void createExclusive(int eventId, int staffId, Supplier<EventAssignment> validatedAssignment);

    void delete(int id);

    /**
     * Elimina la asignación de un coordinador y, en la misma transacción, las
     * que ese coordinador hizo en el evento.
     */
    void deleteWithDelegated(int id);
}
