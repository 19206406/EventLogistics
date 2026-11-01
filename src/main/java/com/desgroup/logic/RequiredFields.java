package com.desgroup.logic;

import com.desgroup.utils.BusinessException;

final class RequiredFields {

    private RequiredFields() {
    }

    static void requireNotBlank(String value, String fieldLabel) {
        if (value == null || value.isBlank())
            throw new BusinessException("El campo '" + fieldLabel + "' es obligatorio.");
    }
}
