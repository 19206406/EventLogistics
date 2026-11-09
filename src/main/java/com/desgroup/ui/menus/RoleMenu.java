package com.desgroup.ui.menus;

import java.util.function.Supplier;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;

import com.desgroup.models.Staff;
import com.desgroup.ui.UiActions;

/**
 * Menú propio de un rol. Cada rol tiene su implementación y se registra en un
 * Map<StaffRole, RoleMenu>, así MainFrame no necesita conocer los roles.
 */
public interface RoleMenu {

    JMenu build(JDesktopPane desktopPane, Staff currentUser);

    /** Las ventanas cargan datos al crearse, por eso se crean dentro de la acción protegida. */
    default void show(JDesktopPane desktopPane, Supplier<JInternalFrame> frameFactory) {
        UiActions.run(desktopPane, () -> {
            JInternalFrame frame = frameFactory.get();
            desktopPane.add(frame);
            frame.setVisible(true);
        });
    }
}
