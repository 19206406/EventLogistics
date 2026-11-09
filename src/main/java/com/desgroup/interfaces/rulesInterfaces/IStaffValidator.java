package com.desgroup.interfaces.rulesInterfaces;

public interface IStaffValidator {
    void validate(String name, String email, String phone, String position, String password, Integer currentId);
}
