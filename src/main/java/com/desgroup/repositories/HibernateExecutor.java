package com.desgroup.repositories;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import com.desgroup.exceptions.BusinessException;
import com.desgroup.exceptions.DataAccessException;

import jakarta.persistence.PersistenceException;

final class HibernateExecutor {

    private static final String TECHNICAL_ERROR = "No se pudo completar la operación en la base de datos.";

    private static final Map<String, String> UNIQUE_CONSTRAINT_MESSAGES = Map.of(
            "uk_staff_email", "Ya existe un miembro del personal con ese correo.",
            "uk_event_assignment_staff_event", "Esta persona ya está asignada al evento.");

    private final SessionFactory sessionFactory;

    HibernateExecutor(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    <R> R read(Function<Session, R> query) {
        return translatingErrors(() -> sessionFactory.fromSession(query));
    }

    void write(Consumer<Session> work) {
        translatingErrors(() -> {
            sessionFactory.inTransaction(work);
            return null;
        });
    }

    private static <R> R translatingErrors(Supplier<R> operation) {
        try {
            return operation.get();
        } catch (PersistenceException ex) {
            throw translate(ex);
        }
    }

    private static RuntimeException translate(PersistenceException ex) {
        for (Throwable cause = ex; cause != null; cause = cause.getCause()) {
            if (cause instanceof ConstraintViolationException violation) {
                String message = UNIQUE_CONSTRAINT_MESSAGES.get(violation.getConstraintName());
                if (message != null)
                    return new BusinessException(message, ex);
            }
        }
        return new DataAccessException(TECHNICAL_ERROR, ex);
    }
}
