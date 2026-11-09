package com.desgroup.interfaces.logicInterfaces;

import com.desgroup.models.Manager;

public interface IManagerService {
    /** @throws com.desgroup.exceptions.BusinessException si el gerente no existe */
    Manager getManagerById(int id);
}
