package com.desgroup.utils;

import java.awt.Component;

import javax.swing.JOptionPane;

public final class MessagesUi {

    private static final String TITLE_ERROR = "Error";
    private static final String TITLE_SUCCESS = "Resultado exitoso";

    private MessagesUi() {
    }

    public static void showError(Component father, String message) {
        JOptionPane.showMessageDialog(father, message, TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
    }

    public static void showSuccess(Component father, String message) {
        JOptionPane.showMessageDialog(father, message, TITLE_SUCCESS, JOptionPane.INFORMATION_MESSAGE);
    }
}
