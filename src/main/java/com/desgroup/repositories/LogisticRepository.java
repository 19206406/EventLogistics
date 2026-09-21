package com.desgroup.repositories;

import java.util.ArrayList;
import java.util.List;

import com.desgroup.models.Logistic;

public class LogisticRepository {
    private List<Logistic> logistics;
    private int nextId;
    private static LogisticRepository instance;
    private void crearLogisticoDePrueba() {
        Logistic logistico = new Logistic(
            "Juan Logistico",              // name
            "logistico",// email
            3001234,                        // phone (int, cuidado con el tamaño)
            "Logistico",                    // position
            "Norte",                        // zone
            "Operativo",                    // role
            "logistico123"                  // password
        );
        logistico.setIdStaff(nextId++);
        logistics.add(logistico);
    }

    public static LogisticRepository getInstance() {
        if (instance == null) {
            instance = new LogisticRepository();
        }
        return instance;
    }
    public LogisticRepository() {
        logistics = new ArrayList<>();
        nextId = 0;
        crearLogisticoDePrueba(); 
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
        logistic.setIdStaff(nextId++); // agregar id antes de agregar
        logistics.add(logistic); // agregar
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
