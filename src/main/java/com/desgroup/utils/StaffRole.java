package com.desgroup.utils;

public enum StaffRole {
    COORDINATOR(1),
    LOGISTIC(2);

    private final int code;

    StaffRole(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
