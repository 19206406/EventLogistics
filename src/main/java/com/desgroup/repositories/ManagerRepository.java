package com.desgroup.repositories;

import java.util.ArrayList;
import java.util.List;

import com.desgroup.interfaces.repositoriesInterfaces.IManagerRepository;
import com.desgroup.models.Manager;

public class ManagerRepository implements IManagerRepository {

    private List<Manager> managers;
    private int nextId;
    private static ManagerRepository instance;

    public ManagerRepository() {
        managers = new ArrayList<>();
        nextId = 0;
        createManagerAdministrator();
    }

    public static ManagerRepository getInstance() {
        if (instance == null) {
            instance = new ManagerRepository();
        }
        return instance;
    }

    private void createManagerAdministrator() {
        Manager admin = new Manager(nextId, "Abelardo", "admin", "1234232342", "Gerente", "admin123", "EventLogistic");
        managers.add(admin);
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
        manager.setIdStaff(nextId++);
        managers.add(manager);
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
