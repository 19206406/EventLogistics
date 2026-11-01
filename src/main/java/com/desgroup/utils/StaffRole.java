package com.desgroup.utils;

public enum StaffRole {
    COORDINATOR(1, "Coordinador"),
    LOGISTIC(2, "Logístico"),
    MANAGER(3, "Gerente");

    private final int code;
    private final String label;

    StaffRole(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}
