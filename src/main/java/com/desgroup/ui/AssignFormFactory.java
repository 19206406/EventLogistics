package com.desgroup.ui;

import javax.swing.JInternalFrame;

@FunctionalInterface
public interface AssignFormFactory<T> {
    JInternalFrame create(T preselected);
}
