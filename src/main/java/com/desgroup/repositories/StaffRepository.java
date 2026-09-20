/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.repositories;

import com.desgroup.models.Staff;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author urreg
 */
public class StaffRepository {
    private LogisticRepository logisticRepository;
    private CoordinatorRepository coordinatorRepository;
    private ManagerRepository managerRepository;

    private List<Staff> staffs;

    public StaffRepository() {
        staffs = new ArrayList<>();
        logisticRepository = new LogisticRepository();
        coordinatorRepository = new CoordinatorRepository();
        managerRepository = new ManagerRepository();
    }

    public List<Staff> getAll() {
        List<Staff> staffs = new ArrayList<>();
        staffs.addAll(logisticRepository.getAll());
        staffs.addAll(coordinatorRepository.getAll());
        staffs.addAll(managerRepository.getAll());
        return staffs;
    }

    public Staff getById(int id) {
        for (Staff staff : staffs) {
            if (staff.getIdStaff() == id) {
                return staff;
            }
        }

        return null;
    }

    public Staff getStaffByEmail(String email) {
        Staff found = logisticRepository.getByEmail(email);

        if (found != null)
            return found;

        found = coordinatorRepository.getByEmail(email);

        if (found != null)
            return found;

        return managerRepository.getByEmail(email);

    }

    public void updated(Staff staff) {
    }

}
