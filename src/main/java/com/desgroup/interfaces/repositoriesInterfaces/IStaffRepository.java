package com.desgroup.interfaces.repositoriesInterfaces;

import java.util.List;

import com.desgroup.models.Staff;

public interface IStaffRepository {
    List<Staff> getAll();

    Staff getById(int id);

    Staff getStaffByEmail(String email);
}
