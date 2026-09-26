package com.desgroup.interfaces.logicInterfaces;

import com.desgroup.models.Staff;

public interface IStaffService {
    String showStaffPosition(int id);

    double showStaffSalary(int id);

    boolean staffLogin(String email, String password);

    void changeStaffPassword(int id, String password);

    Staff login(String email, String password);
}
