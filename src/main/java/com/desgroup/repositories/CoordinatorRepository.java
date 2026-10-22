package com.desgroup.repositories;

import java.util.ArrayList;
import java.util.List;

import com.desgroup.interfaces.repositoriesInterfaces.ICoordinatorRepository;
import com.desgroup.models.Coordinator;
import com.desgroup.utils.StaffIdGenerator;

public class CoordinatorRepository implements ICoordinatorRepository {

    private List<Coordinator> coordinators;

    public CoordinatorRepository() {
        coordinators = new ArrayList<>();
        createCoordinadorAdministrator();
    }

    private void createCoordinadorAdministrator() {
        Coordinator coordinator = new Coordinator(StaffIdGenerator.next(), "Sebastian", "sebastian@gmail.com", "3005389944", "Coordinador", "sebastian123", "Event S.A.S");
        coordinators.add(coordinator);
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
        coordinator.setIdStaff(StaffIdGenerator.next());
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
