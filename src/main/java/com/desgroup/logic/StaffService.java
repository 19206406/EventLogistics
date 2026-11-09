package com.desgroup.logic;

import com.desgroup.exceptions.BusinessException;
import com.desgroup.interfaces.logicInterfaces.IStaffService;
import com.desgroup.interfaces.repositoriesInterfaces.IStaffRepository;
import com.desgroup.models.Staff;

public class StaffService implements IStaffService {

    private final IStaffRepository repository;

    public StaffService(IStaffRepository repository) {
        this.repository = repository;
    }

    @Override
    public Staff login(String email, String password) {
        Staff staff = repository.getByEmail(email);

        if (staff == null || !staff.getPassword().equals(password))
            throw new BusinessException("Correo o contraseña incorrectos.");

        return staff;
    }

}
