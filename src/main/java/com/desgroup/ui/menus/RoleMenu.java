package com.desgroup.ui.menus;

import java.util.function.Supplier;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;

import com.desgroup.models.Staff;
import com.desgroup.ui.UiActions;

public interface RoleMenu {

    JMenu build(JDesktopPane desktopPane, Staff currentUser);

    default void show(JDesktopPane desktopPane, Supplier<JInternalFrame> frameFactory) {
        UiActions.run(desktopPane, () -> {
            JInternalFrame frame = frameFactory.get();
            desktopPane.add(frame);
            frame.setVisible(true);
        });
    }
}
