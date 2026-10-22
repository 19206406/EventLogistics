package com.desgroup.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.desgroup.interfaces.repositoriesInterfaces.IEventRepository;
import com.desgroup.models.Event;
import com.desgroup.models.Place;

public class EventRepository implements IEventRepository {

    private List<Event> events;
    private int nextId;
    private int placeId;

    public EventRepository() {
        events = new ArrayList<>();
        nextId = 1;
        placeId = 1;
        createEvents();
    }

    private void createEvents() {
        Place p1 = new Place(
                "Colombia",
                "Medellín",
                "Plaza Mayor Medellín",
                "Calle 41 # 55-80",
                3000);

        Place p2 = new Place(
                "Colombia",
                "Medellín",
                "Estadio Atanasio Girardot",
                "Calle 48 # 73-10",
                4000);

        Place p3 = new Place(
                "Colombia",
                "Medellín",
                "Jardín Botánico de Medellín",
                "Calle 73 # 51D-14",
                1000);

        Place p4 = new Place(
                "Colombia",
                "Medellín",
                "Teatro Metropolitano",
                "Calle 41 # 57-30",
                800);

        Place p5 = new Place(
                "Colombia",
                "Medellín",
                "Centro de Convenciones El Tesoro",
                "Carrera 25A # 1A Sur-45",
                600);

        events.add(new Event(
                30, 10, 3, 18, 8,
                LocalDate.of(2026, 11, 15),
                "Programado", p1,
                "Feria de Emprendimiento e Innovación", 1));

        events.add(new Event(
                60, 15, 4, 22, 14,
                LocalDate.of(2026, 11, 21),
                "Programado", p2,
                "Festival Musical de Medellín", 2));

        events.add(new Event(
                12, 4, 3, 17, 9,
                LocalDate.of(2026, 11, 28),
                "Programado", p3,
                "Jornada Ambiental y de Sostenibilidad", 3));

        events.add(new Event(
                16, 4, 4, 21, 15,
                LocalDate.of(2026, 12, 5),
                "Programado", p4,
                "Gala Cultural y Artística", 4));

        events.add(new Event(
                20, 5, 4, 19, 8,
                LocalDate.of(2026, 12, 12),
                "Programado", p5,
                "Congreso de Tecnología y Desarrollo de Software", 5));

        events.add(new Event(
                24, 6, 4, 20, 10,
                LocalDate.of(2026, 12, 19),
                "En preparación", p1,
                "Expoferia de Proyectos Universitarios", 6));
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
        event.setIdEvent(nextId++);
        event.getPlace().setIdPlace(placeId++);
        events.add(event);
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
