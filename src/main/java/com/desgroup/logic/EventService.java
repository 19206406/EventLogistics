package com.desgroup.logic;

import java.time.LocalDate;
import java.util.List;

import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.interfaces.repositoriesInterfaces.IAssignmentCounter;
import com.desgroup.interfaces.repositoriesInterfaces.IEventRepository;
import com.desgroup.interfaces.rulesInterfaces.IEventValidator;
import com.desgroup.models.Event;
import com.desgroup.models.EventState;
import com.desgroup.models.Place;
import com.desgroup.exceptions.BusinessException;
import com.desgroup.utils.StaffRole;

public class EventService implements IEventService {

    private final IEventRepository eventRepository;
    private final IAssignmentCounter assignmentCounter;
    private final IEventValidator eventValidator;

    public EventService(IEventRepository eventRepository, IAssignmentCounter assignmentCounter,
            IEventValidator eventValidator) {
        this.eventRepository = eventRepository;
        this.assignmentCounter = assignmentCounter;
        this.eventValidator = eventValidator;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.getAll();
    }

    @Override
    public Event getEventById(int id) {
        Event event = eventRepository.getById(id);
        if (event == null)
            throw new BusinessException("El evento no existe.");

        return event;
    }

    @Override
    public void createEvent(String name, EventState state, LocalDate date, int startTime, String country,
            String city, String placeName, String address, int capacity, int endTime, int maxCoordinators,
            int maxLogistics, int maxLogisticsPerCoordinator) {

        eventValidator.validate(name, state, date, startTime, endTime, country, city, placeName, address, capacity,
                maxCoordinators, maxLogistics, maxLogisticsPerCoordinator);

        Place place = new Place(country, city, placeName, address, capacity);
        Event newEvent = new Event(maxLogistics, maxLogisticsPerCoordinator, maxCoordinators, endTime, startTime,
                date, state, place, name);
        eventRepository.create(newEvent);
    }

    @Override
    public void updateEvent(int idEvent, String name, EventState state, LocalDate date, int startTime, String country,
            String city, String placeName, String address, int capacity, int endTime, int maxCoordinators,
            int maxLogistics, int maxLogisticsPerCoordinator) {
        Event event = getEventById(idEvent);

        eventValidator.validate(name, state, date, startTime, endTime, country, city, placeName, address, capacity,
                maxCoordinators, maxLogistics, maxLogisticsPerCoordinator);

        if (maxCoordinators < assignmentCounter.countByEventAndRole(idEvent, StaffRole.COORDINATOR))
            throw new BusinessException("Hay más coordinadores asignados que el nuevo máximo.");
        if (maxLogistics < assignmentCounter.countByEventAndRole(idEvent, StaffRole.LOGISTIC))
            throw new BusinessException("Hay más logísticos asignados que el nuevo máximo.");

        Place place = event.getPlace();
        place.setCountry(country);
        place.setCity(city);
        place.setPlaceName(placeName);
        place.setAddress(address);
        place.setCapacity(capacity);
        event.setName(name);
        event.setState(state);
        event.setDate(date);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        event.setMaxCoordinators(maxCoordinators);
        event.setMaxLogistics(maxLogistics);
        event.setMaxLogisticsPerCoordinator(maxLogisticsPerCoordinator);
        eventRepository.updated(event);
    }

    @Override
    public void deleteEventById(int id) {
        getEventById(id);
        eventRepository.deleteWithAssignments(id);
    }

}
