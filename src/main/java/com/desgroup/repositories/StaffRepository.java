/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.repositories;

import com.desgroup.interfaces.repositoriesInterfaces.ICoordinatorRepository;
import com.desgroup.interfaces.repositoriesInterfaces.ILogisticRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IManagerRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IStaffRepository;
import com.desgroup.models.Staff;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author urreg
 */
public class StaffRepository implements IStaffRepository {
    private List<Staff> staffs;
    private final ILogisticRepository logisticRepository;
    private final ICoordinatorRepository coordinatorRepository;
    private final IManagerRepository managerRepository;

    public StaffRepository(ILogisticRepository logisticRepository,
            ICoordinatorRepository coordinatorRepository,
            IManagerRepository managerRepository) {
        this.logisticRepository = logisticRepository;
        this.coordinatorRepository = coordinatorRepository;
        this.managerRepository = managerRepository;
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
}
