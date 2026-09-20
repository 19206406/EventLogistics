package com.desgroup.logic;

import com.desgroup.models.Staff;
import com.desgroup.repositories.StaffRepository;

public class StaffService {
    // debo de preguntar si esto se puede o como puedo consultar esta maricada
    // osea como tengo los empleados completos para el inicio de sesión
    private StaffRepository repository;

    public StaffService() {
        repository = new StaffRepository();
    }

    public String showStaffPosition(int id) {
        Staff staff = repository.getById(id);
        return staff.getPosition();
    }

    public double showStaffSalary(int id) {
        Staff staff = repository.getById(id);
        return staff.getSalary();
    }

    public boolean staffLogin(String email, String password) {
        Staff staff = repository.getStaffByEmail(email);

        if(staff.getEmail().equals(email) && staff.getPassword().equals(password)) {
            return true;
        }else  {
            return false;
        }

    }

    public void changeStaffPassword(int id, String password) {
        Staff staff = repository.getById(id);
        staff.setPassword(password);

    }

    // no se si mostrar evento le corresponde a este servicio o incluso.
    // si le corresponde a eventos.
}
