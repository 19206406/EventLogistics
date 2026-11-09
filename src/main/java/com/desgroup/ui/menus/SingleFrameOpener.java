package com.desgroup.ui.menus;

import java.beans.PropertyVetoException;
import java.util.function.Supplier;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import com.desgroup.ui.UiActions;

/**
 * Abre una ventana interna una sola vez: si ya está abierta, la trae al frente
 * en lugar de crear otra.
 */
final class SingleFrameOpener {

    private JInternalFrame frame;

    void open(JDesktopPane desktopPane, Supplier<JInternalFrame> frameFactory) {
        if (frame != null && !frame.isClosed()) {
            frame.toFront();
            try {
                frame.setSelected(true);
            } catch (PropertyVetoException ignored) {
            }
            return;
        }
        UiActions.run(desktopPane, () -> {
            frame = frameFactory.get();
            desktopPane.add(frame);
            frame.setVisible(true);
        });
    }
}
