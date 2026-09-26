package com.desgroup.repositories;

import java.util.ArrayList;
import java.util.List;

import com.desgroup.interfaces.repositoriesInterfaces.ICoordinatorRepository;
import com.desgroup.models.Coordinator;

public class CoordinatorRepository implements ICoordinatorRepository {

    private List<Coordinator> coordinators;
    private int nextId;

    public CoordinatorRepository() {
        coordinators = new ArrayList<>();
        nextId = 0;
        initializeLogistics();
    }

    private void initializeLogistics() {

        coordinators.add(new Coordinator(nextId++, "Robert", "robert@gmail.com", "3025896", "Coordinadora",
                "robert123", "EventLogistic"));

        coordinators.add(new Coordinator(nextId++, "Carolina", "carolina@gmail.com", "3014732", "Coordinadora",
                "Carolina123", "EventLogistic"));

        coordinators.add(new Coordinator(nextId++, "Valentina", "valentina@gmail.com", "3008254", "Coordinadora",
                "Valentina123", "EventLogistic"));

        coordinators.add(new Coordinator(nextId++, "Daniela", "daniela@gmail.com", "3046918", "Coordinadora",
                "Daniela123", "EventLogistic"));

        coordinators.add(new Coordinator(nextId++, "Maria", "maria@gmail.com", "3052376", "Coordinadora",
                "Maria123", "EventLogistic"));

        coordinators.add(new Coordinator(nextId++, "Gabriela", "gabriela@gmail.com", "3065149", "Coordinadora",
                "Gabriela123", "EventLogistic"));

        coordinators.add(new Coordinator(nextId++, "Natalia", "natalia@gmail.com", "3078263", "Coordinadora",
                "Natalia123", "EventLogistic"));

        coordinators.add(new Coordinator(nextId++, "Paula", "paula@gmail.com", "3083497", "Coordinadora",
                "Paula123", "EventLogistic"));

        coordinators.add(new Coordinator(nextId++, "Laura", "laura@gmail.com", "3096721", "Coordinadora",
                "Laura123", "EventLogistic"));

        coordinators.add(new Coordinator(nextId++, "Alejandra", "alejandra@gmail.com", "3104586", "Coordinadora",
                "Alejandra123", "EventLogistic"));

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
        coordinator.setIdStaff(nextId++);
        coordinators.add(coordinator);
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
