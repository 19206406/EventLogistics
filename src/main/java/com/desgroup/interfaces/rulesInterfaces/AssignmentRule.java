package com.desgroup.interfaces.rulesInterfaces;

import com.desgroup.models.Event;
import com.desgroup.models.Staff;

/**
 * Regla que valida una asignación.
 * Contrato: funciona para cualquier actor y target. Lanza BusinessException si
 * la asignación no cumple la regla y no modifica nada.
 */
public interface AssignmentRule {
    void check(Staff actor, Staff target, Event event);
}
