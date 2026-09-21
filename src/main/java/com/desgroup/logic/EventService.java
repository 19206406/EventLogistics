package com.desgroup.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.desgroup.models.Event;
import com.desgroup.models.Place;
import com.desgroup.repositories.EventRepository;

public class EventService {

    private EventRepository repository;

    public EventService() {
        repository = new EventRepository();
    }

    public List<Event> getAllEvents() {
        return repository.getAll();
    }

    public Event getEventById(int id) {
        return repository.getById(id);
    }

    // directamente se crea tanto el evento como el lugar donde este va ser
    public void createEvent(String name, String state, LocalDate date, int startTime, String country,
            String city, String placeName, String address, int capacity) {
        Place place = new Place(country, city, placeName, address, capacity);
        Event newEvent = new Event(capacity, placeName, place, state, date, startTime);
        repository.create(newEvent);
    }

    // de igual forma pasa igual que en la creación
    public void updatedEvent(String name, String state, LocalDate date, int startTime, String country,
            String city, String placeName, String address, int capacity) {
        Place place = new Place(country, city, placeName, address, capacity);
        Event event = new Event(capacity, placeName, place, state, date, startTime);
        repository.updated(event);
    }

    // lista de ids de eventos y trae la información de estos para mostrar por
    // ejemplo asignaciones de una persona
    public List<Event> getEventsByArrayIds(List<Integer> ids) {
        List<Event> events = new ArrayList<>();

        for (int id : ids) {
            events.add(repository.getById(id));
        }

        return events;
    }
    
    public void deleteEventById(int id) {
        repository.delete(id);
    }
}
