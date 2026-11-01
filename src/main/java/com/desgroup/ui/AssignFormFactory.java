package com.desgroup.ui;

import javax.swing.JInternalFrame;

/**
 * Crea el formulario de asignación a un evento, con un elemento preseleccionado
 * (puede ser null). Así el frame que lo abre no depende de la clase concreta
 * del formulario ni de los servicios que esta necesita.
 */
@FunctionalInterface
public interface AssignFormFactory<T> {
    JInternalFrame create(T preselected);
}
