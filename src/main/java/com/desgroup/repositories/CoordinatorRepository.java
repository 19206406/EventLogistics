package com.desgroup.repositories;

import java.util.ArrayList;
import java.util.List;

import com.desgroup.models.Coordinator;

public class CoordinatorRepository {

    private List<Coordinator> coordinators;
    private int nextId;

    public CoordinatorRepository() {
        coordinators = new ArrayList<>();
        nextId = 0;
    }

    public List<Coordinator> getAll() {
        return new ArrayList<>(coordinators);
    }

    public Coordinator getById(int id) {
        for (Coordinator coordinator : coordinators) {
            if (coordinator.getIdStaff() == id) {
                return coordinator;
            }
        }

        return null;
    }

    public Coordinator getByEmail(String email) {
        for (Coordinator coordinator : coordinators) {
            if (coordinator.getEmail().equals(email)) {
                return coordinator;
            }
        }
        return null;
    }

    public void create(Coordinator coordinator) {
        coordinator.setIdStaff(nextId++); // agregar id antes de agregar
        coordinators.add(coordinator); // agregar
    }

    public void updated(Coordinator coordinator) {
        for (int i = 0; i < coordinators.size(); i++) {
            if (coordinators.get(i).getIdStaff() == coordinator.getIdStaff()) {
                coordinators.set(i, coordinator);
                return;
            }
        }
    }

    public void delete(int id) {
        coordinators.removeIf(l -> l.getIdStaff() == id);
    }

}
