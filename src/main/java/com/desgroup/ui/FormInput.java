package com.desgroup.ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JTextField;

import com.desgroup.utils.BusinessException;

final class FormInput {

    private FormInput() {
    }

    static String text(JTextField field) {
        return field.getText().trim();
    }

    static int parseInt(JTextField field, String fieldLabel) {
        try {
            return Integer.parseInt(field.getText().trim());
        } catch (NumberFormatException e) {
            throw new BusinessException("El campo '" + fieldLabel + "' debe ser un número entero");
        }
    }

    static LocalDate parseDate(JTextField field, String fieldLabel) {
        try {
            return LocalDate.parse(field.getText().trim());
        } catch (DateTimeParseException e) {
            throw new BusinessException("El campo '" + fieldLabel
                    + "' no es válido. Use el formato AAAA-MM-DD (por ejemplo 2026-10-25)");
        }
    }
}
