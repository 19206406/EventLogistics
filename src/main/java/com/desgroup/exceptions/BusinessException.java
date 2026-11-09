package com.desgroup.exceptions;

/**
 * Regla de negocio incumplida o dato inválido. Su mensaje está pensado para
 * mostrarse tal cual al usuario.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
