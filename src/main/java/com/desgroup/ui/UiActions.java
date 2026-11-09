package com.desgroup.ui;

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.desgroup.exceptions.BusinessException;
import com.desgroup.exceptions.DataAccessException;

/**
 * Punto único donde la interfaz convierte las excepciones en mensajes para el
 * usuario.
 */
public final class UiActions {

    private static final Logger LOGGER = Logger.getLogger(UiActions.class.getName());

    private UiActions() {
    }

    /**
     * Ejecuta una acción de la interfaz y muestra al usuario cualquier error
     * esperado.
     *
     * @return true si la acción terminó sin errores
     */
    public static boolean run(Component parent, Runnable action) {
        try {
            action.run();
            return true;
        } catch (BusinessException ex) {
            JOptionPane.showMessageDialog(parent, ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        } catch (DataAccessException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            JOptionPane.showMessageDialog(parent, ex.getMessage() + "\nIntenta de nuevo más tarde.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    /** Red de seguridad para los errores de programación que nadie capturó. */
    public static void installGlobalHandler() {
        Thread.setDefaultUncaughtExceptionHandler((thread, error) -> {
            LOGGER.log(Level.SEVERE, "Error inesperado en el hilo " + thread.getName(), error);
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,
                    "Ocurrió un error inesperado. La última acción pudo no completarse.", "Error",
                    JOptionPane.ERROR_MESSAGE));
        });
    }
}
