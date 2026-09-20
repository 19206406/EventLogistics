package com.desgroup.repositories;

import java.util.ArrayList;
import java.util.List;

import com.desgroup.models.Event;

public class EventRepository {

    private List<Event> events;
    private int nextId;
    private int placeId;

    public EventRepository() {
        events = new ArrayList<>();
        nextId = 0;
        placeId = 0;
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
