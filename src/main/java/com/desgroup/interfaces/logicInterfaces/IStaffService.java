package com.desgroup.interfaces.logicInterfaces;

import com.desgroup.models.Staff;

public interface IStaffService {
    /** @throws com.desgroup.exceptions.BusinessException si las credenciales son incorrectas */
    Staff login(String email, String password);
}
