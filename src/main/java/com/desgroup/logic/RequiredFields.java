package com.desgroup.logic;

import com.desgroup.exceptions.BusinessException;

final class RequiredFields {

    private static final int MAX_TEXT_LENGTH = 255;

    private RequiredFields() {
    }

    static void requireText(String value, String fieldLabel) {
        if (value == null || value.isBlank())
            throw new BusinessException("El campo '" + fieldLabel + "' es obligatorio.");
        if (value.length() > MAX_TEXT_LENGTH)
            throw new BusinessException("El campo '" + fieldLabel + "' no puede superar los "
                    + MAX_TEXT_LENGTH + " caracteres.");
    }
}
