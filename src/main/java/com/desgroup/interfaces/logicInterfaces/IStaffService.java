package com.desgroup.interfaces.logicInterfaces;

import com.desgroup.models.Staff;

public interface IStaffService {
    Staff login(String email, String password);
}
