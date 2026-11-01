package com.desgroup.ui.menus;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;

import com.desgroup.models.Staff;

/**
 * Menú propio de un rol. Cada rol tiene su implementación y se registra en un
 * Map<StaffRole, RoleMenu>, así MainFrame no necesita conocer los roles.
 */
public interface RoleMenu {

    JMenu build(JDesktopPane desktopPane, Staff currentUser);

    default void show(JDesktopPane desktopPane, JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}
