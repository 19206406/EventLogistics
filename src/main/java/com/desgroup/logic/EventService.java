package com.desgroup.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.models.Event;
import com.desgroup.models.Place;
import com.desgroup.repositories.EventRepository;

public class EventService implements IEventService {

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

    public void createEvent(String name, String state, LocalDate date, int startTime, String country,
            String city, String placeName, String address, int capacity) {
        Place place = new Place(country, city, placeName, address, capacity);
        Event newEvent = new Event(capacity, name, place, state, date, startTime);
        repository.create(newEvent);
    }

    public void updatedEvent(int idEvent, String name, String state, LocalDate date, int startTime, String country,
            String city, String placeName, String address, int capacity) {
        Place place = new Place(country, city, placeName, address, capacity);
        Event event = new Event(capacity, name, place, state, date, startTime);
        event.setIdEvent(idEvent);
        repository.updated(event);
    }

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
