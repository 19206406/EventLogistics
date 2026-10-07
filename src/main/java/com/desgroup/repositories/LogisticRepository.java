package com.desgroup.repositories;

import java.util.ArrayList;
import java.util.List;

import com.desgroup.interfaces.repositoriesInterfaces.ILogisticRepository;
import com.desgroup.models.Logistic;
import com.desgroup.utils.StaffIdGenerator;

public class LogisticRepository implements ILogisticRepository {
    private List<Logistic> logistics;
    private int nextId;

    public LogisticRepository() {
        logistics = new ArrayList<>();
        nextId = 0;
        initializeLogistics();
    }

    private void initializeLogistics() {

        logistics.add(new Logistic(StaffIdGenerator.next(), "Roberto", "roberto@gmail.com", "30147852", "Logistico", "roberto123",
                "Norte", "Vigilancia", 4, 20));

        logistics.add(new Logistic(StaffIdGenerator.next(), "Sebastian", "sebastian@gmail.com", "3005389", "Logistico", "sebas123",
                "Norte", "Vigilancia", 4, 10));

        logistics.add(new Logistic(StaffIdGenerator.next(), "Camilo", "camilo@gmail.com", "3014527", "Logistico", "camilo123",
                "Sur", "Transporte", 6, 23));

        logistics.add(new Logistic(StaffIdGenerator.next(), "Andres", "andres@gmail.com", "3027814", "Logistico", "andres123",
                "Centro", "Montaje", 8, 12));

        logistics.add(new Logistic(StaffIdGenerator.next(), "Mateo", "mateo@gmail.com", "3036251", "Logistico", "mateo123",
                "Occidente", "Seguridad", 5, 11));

        logistics.add(new Logistic(StaffIdGenerator.next(), "Nicolas", "nicolas@gmail.com", "3049183", "Logistico", "nicolas123",
                "Oriente", "Sonido", 7, 32));

        logistics.add(new Logistic(StaffIdGenerator.next(), "Daniel", "daniel@gmail.com", "3053476", "Logistico", "daniel123",
                "Norte", "Iluminacion", 9, 11));

        logistics.add(new Logistic(StaffIdGenerator.next(), "Juan", "juan@gmail.com", "3065829", "Logistico", "juan123",
                "Sur", "Vigilancia", 3, 23));

        logistics.add(new Logistic(StaffIdGenerator.next(), "Felipe", "felipe@gmail.com", "3072145", "Logistico", "felipe123",
                "Centro", "Transporte", 10, 21));

        logistics.add(new Logistic(StaffIdGenerator.next(), "Santiago", "santiago@gmail.com", "3087632", "Logistico", "santiago123",
                "Occidente", "Montaje", 6, 23));

        logistics.add(new Logistic(StaffIdGenerator.next(), "Julian", "julian@gmail.com", "3094258", "Logistico", "julian123",
                "Oriente", "Seguridad", 8, 11));
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
        logistic.setIdStaff(nextId++);
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
