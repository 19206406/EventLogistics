package com.desgroup.interfaces.logicInterfaces;

import java.time.LocalDate;
import java.util.List;

import com.desgroup.models.Event;

public interface IEventService {

        List<Event> getAllEvents();

        Event getEventById(int id);

        void createEvent(String name, String state, LocalDate date, int startTime, String country,
                        String city, String placeName, String address, int capacity);

        void updatedEvent(int idEvent, String name, String state, LocalDate date, int startTime, String country,
                        String city, String placeName, String address, int capacity);

        List<Event> getEventsByArrayIds(List<Integer> ids);

        void deleteEventById(int id);
}
