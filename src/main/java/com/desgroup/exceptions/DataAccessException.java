package com.desgroup.exceptions;

/**
 * Fallo técnico al acceder a los datos. Los repositorios traducen a este tipo
 * las excepciones de Hibernate, para que las demás capas no dependan de él.
 */
public class DataAccessException extends RuntimeException {
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
