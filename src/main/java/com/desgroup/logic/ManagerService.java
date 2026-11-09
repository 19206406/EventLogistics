package com.desgroup.logic;

import com.desgroup.interfaces.logicInterfaces.IManagerService;
import com.desgroup.interfaces.repositoriesInterfaces.IManagerRepository;
import com.desgroup.models.Manager;
import com.desgroup.exceptions.BusinessException;

public class ManagerService implements IManagerService {

    private final IManagerRepository managerRepository;

    public ManagerService(IManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public Manager getManagerById(int id) {
        Manager manager = managerRepository.getById(id);
        if (manager == null)
            throw new BusinessException("El gerente no existe");

        return manager;
    }

}
