package com.desgroup.repositories;

import java.util.ArrayList;
import java.util.List;

import com.desgroup.interfaces.repositoriesInterfaces.ILogisticRepository;
import com.desgroup.models.Logistic;
import com.desgroup.utils.StaffIdGenerator;

public class LogisticRepository implements ILogisticRepository {
    private List<Logistic> logistics;

    public LogisticRepository() {
        logistics = new ArrayList<>();
        logisticsCreate();
    }

    private void logisticsCreate() {
        logistics.add(new Logistic(
                1,
                "Andrés Felipe Restrepo",
                "andres.restrepo@example.com",
                "3004567891",
                "Personal logístico",
                "Logistic2026!",
                "Zona norte",
                "Montaje",
                85,
                6
        ));

        logistics.add(new Logistic(
                2,
                "Laura Camila Gómez",
                "laura.gomez@example.com",
                "3105678923",
                "Personal logístico",
                "Logistic2026!",
                "Zona central",
                "Registro de asistentes",
                92,
                8
        ));

        logistics.add(new Logistic(
                3,
                "Juan David Martínez",
                "juan.martinez@example.com",
                "3206789134",
                "Personal logístico",
                "Logistic2026!",
                "Zona sur",
                "Control de acceso",
                78,
                4
        ));
    }

    public List<Logistic> getAll() {
        return new ArrayList<>(logistics);
    }

    public Logistic getById(int id) {
        for (Logistic logistic : logistics) {
            if (logistic.getIdStaff() == id) {
                return logistic;
            }
        }

        return null;
    }

    public Logistic getByEmail(String email) {
        for (Logistic logistic : logistics) {
            if (logistic.getEmail().equals(email)) {
                return logistic;
            }
        }
        return null;
    }

    public void create(Logistic logistic) {
        logistic.setIdStaff(StaffIdGenerator.next());
        logistics.add(logistic);
    }

    public void updated(Logistic logistic) {
        for (int i = 0; i < logistics.size(); i++) {
            if (logistics.get(i).getIdStaff() == logistic.getIdStaff()) {
                logistics.set(i, logistic);
                return;
            }
        }
    }

    public void delete(int id) {
        logistics.removeIf(l -> l.getIdStaff() == id);
    }
}
