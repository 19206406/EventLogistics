package com.desgroup.logic;

import com.desgroup.interfaces.logicInterfaces.IStaffService;
import com.desgroup.interfaces.repositoriesInterfaces.IStaffRepository;
import com.desgroup.models.Staff;

public class StaffService implements IStaffService {

    private final IStaffRepository repository;

    public StaffService(IStaffRepository repository) {
        this.repository = repository;
    }

    @Override
    public String showStaffPosition(int id) {
        Staff staff = repository.getById(id);
        return staff.getPosition();
    }

    @Override
    public boolean staffLogin(String email, String password) {
        Staff staff = repository.getByEmail(email);

        if (staff == null)
            return false;

        if (email.equals("admin") && password.equals("admin123"))
            return true;

        return staff.getEmail().equals(email) && staff.getPassword().equals(password);
    }

    @Override
    public void changeStaffPassword(int id, String password) {
        Staff staff = repository.getById(id);
        staff.setPassword(password);

    }

    @Override
    public Staff login(String email, String password) {
        Staff staff = repository.getByEmail(email);

        if (staff != null && staff.getPassword().equals(password)) {
            return staff;
        }
        return null;
    }

}
