package com.desgroup.repositories;

import java.util.ArrayList;
import java.util.List;

import com.desgroup.models.Manager;

public class ManagerRepository {

    private List<Manager> managers;
    private int nextId;

    public ManagerRepository() {
        managers = new ArrayList<>();
        nextId = 0;
    }

    public List<Manager> getAll() {
        return new ArrayList<>(managers);
    }

    public Manager getById(int id) {
        for (Manager manager : managers) {
            if (manager.getIdStaff() == id) {
                return manager;
            }
        }

        return null;
    }

    public Manager getByEmail(String email) {
        for (Manager manager : managers) {
            if (manager.getEmail().equals(email)) {
                return manager;
            }
        }
        return null;
    }

    public void create(Manager manager) {
        manager.setIdStaff(nextId++); // agregar id antes de agregar
        managers.add(manager); // agregar
    }

    public void updated(Manager manager) {
        for (int i = 0; i < managers.size(); i++) {
            if (managers.get(i).getIdStaff() == manager.getIdStaff()) {
                managers.set(i, manager);
                return;
            }
        }
    }

    public void delete(int id) {
        managers.removeIf(l -> l.getIdStaff() == id);
    }
}
