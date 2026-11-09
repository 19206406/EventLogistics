package com.desgroup.interfaces.logicInterfaces;

import java.time.LocalDate;
import java.util.List;

import com.desgroup.models.Event;
import com.desgroup.models.EventState;

public interface IEventService {

        List<Event> getAllEvents();

        Event getEventById(int id);

        void createEvent(String name, EventState state, LocalDate date, int startTime, String country,
                        String city, String placeName, String address, int capacity, int endTime, int maxCoordinators,
                        int maxLogistics, int maxLogisticsPerCoordinator);

        void updateEvent(int idEvent, String name, EventState state, LocalDate date, int startTime, String country,
                        String city, String placeName, String address, int capacity, int endTime, int maxCoordinators,
                        int maxLogistics, int maxLogisticsPerCoordinator);

        void deleteEventById(int id);
}
