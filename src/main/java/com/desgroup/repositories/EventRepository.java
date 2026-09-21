package com.desgroup.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.desgroup.models.Event;
import com.desgroup.models.Place;

public class EventRepository {

    private List<Event> events;
    private int nextId;
    private int placeId;

    public EventRepository() {
        events = new ArrayList<>();
        nextId = 0;
        placeId = 0;
        initializeLogistics();
    }

    private void initializeLogistics() {
        Place place = new Place("Colombia", "Medellín", "Estadio metropolitano", "Laureles", nextId++);
        events.add(new Event(placeId++, "Cumbias", place, "Activo", LocalDate.of(2026, 9, 4), 12));

        Place place2 = new Place("Colombia", "Bogotá", "Movistar Arena", "Barrios Unidos", nextId++);
        events.add(new Event(placeId++, "Festival de Rock", place2, "Activo", LocalDate.of(2026, 9, 10), 25));

        Place place3 = new Place("Colombia", "Cali", "Arena Cañaveralejo", "Cañaveralejo", nextId++);
        events.add(new Event(placeId++, "Salsa en Cali", place3, "Activo", LocalDate.of(2026, 9, 15), 18));

        Place place4 = new Place("Colombia", "Cartagena", "Centro de Convenciones", "Getsemaní", nextId++);
        events.add(new Event(placeId++, "Concierto Caribe", place4, "Activo", LocalDate.of(2026, 9, 20), 30));

        Place place5 = new Place("Colombia", "Barranquilla", "Estadio Metropolitano", "Ciudadela 20 de Julio",
                nextId++);
        events.add(new Event(placeId++, "Carnaval Musical", place5, "Activo", LocalDate.of(2026, 10, 2), 40));

        Place place6 = new Place("Colombia", "Pereira", "Expofuturo", "Álamos", nextId++);
        events.add(new Event(placeId++, "Feria Cultural", place6, "Activo", LocalDate.of(2026, 10, 8), 15));

        Place place7 = new Place("Colombia", "Manizales", "Plaza de Toros", "San Jorge", nextId++);
        events.add(new Event(placeId++, "Festival Andino", place7, "Activo", LocalDate.of(2026, 10, 14), 22));

        Place place8 = new Place("Colombia", "Bucaramanga", "Neomundo", "Tejar", nextId++);
        events.add(new Event(placeId++, "Noche de Música", place8, "Activo", LocalDate.of(2026, 10, 20), 35));

        Place place9 = new Place("Colombia", "Santa Marta", "Quinta de San Pedro Alejandrino", "Mamatoco", nextId++);
        events.add(new Event(placeId++, "Festival del Caribe", place9, "Activo", LocalDate.of(2026, 11, 5), 20));

        Place place10 = new Place("Colombia", "Armenia", "Centro Cultural Metropolitano", "Centro", nextId++);
        events.add(new Event(placeId++, "Encuentro Cultural", place10, "Activo", LocalDate.of(2026, 11, 12), 16));
    }

    public List<Event> getAll() {
        return new ArrayList<>(events);
    }

    public Event getById(int id) {
        for (Event event : events) {
            if (event.getIdEvent() == id) {
                return event;
            }
        }

        return null;
    }

    public void create(Event event) {
        event.setIdEvent(nextId++); // agregar id antes de agregar
        event.getPlace().setIdPlace(placeId++);
        events.add(event); // agregar
    }

    public void updated(Event event) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getIdEvent() == event.getIdEvent()) {
                events.set(i, event);
                return;
            }
        }
    }

    public void delete(int id) {
        events.removeIf(l -> l.getIdEvent() == id);
    }
}
