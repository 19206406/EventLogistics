package com.desgroup.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.interfaces.repositoriesInterfaces.IEventRepository;
import com.desgroup.models.Event;
import com.desgroup.models.Place;
import com.desgroup.utils.BusinessException;
import com.desgroup.utils.StaffRole;

public class EventService implements IEventService {

    private final IEventRepository eventRepository;
    private final IEventAssignmentRepository assignmentRepository;

    public EventService(IEventRepository eventRepository, IEventAssignmentRepository assignmentRepository) {
        this.eventRepository = eventRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.getAll();
    }

    public Event getEventById(int id) {
        return eventRepository.getById(id);
    }

    public void createEvent(String name, String state, LocalDate date, int startTime, String country,
            String city, String placeName, String address, int capacity) {
        Place place = new Place(country, city, placeName, address, capacity);
        Event newEvent = new Event(capacity, name, place, state, date, startTime);
        eventRepository.create(newEvent);
    }

    public void updatedEvent(int idEvent, String name, String state, LocalDate date, int startTime, String country,
            String city, String placeName, String address, int capacity, int endTime, int maxCoordinators, int maxLogistics, int maxLogisticsPerCoordinator) {
        Event event = eventRepository.getById(idEvent);
        if (event == null) throw new BusinessException("El evento no existe.");

        validateLimits(startTime, endTime, maxCoordinators, maxLogistics, maxLogisticsPerCoordinator);

        // no bajar los cupos por debajo de lo ya asignado
        if (maxCoordinators < assignmentRepository.countByEventAndRole(idEvent, StaffRole.COORDINATOR))
            throw new BusinessException("Hay más coordinadores asignados que el nuevo máximo.");
        if (maxLogistics < assignmentRepository.countByEventAndRole(idEvent, StaffRole.LOGISTIC))
            throw new BusinessException("Hay más logísticos asignados que el nuevo máximo.");

        // modificar el existente conserva idPlace y las asignaciones
        Place place = event.getPlace();
        place.setCountry(country); place.setCity(city); place.setPlaceName(placeName);
        place.setAddress(address);  place.setCapacity(capacity);
        event.setName(name); event.setState(state); event.setDate(date);
        event.setStartTime(startTime); event.setEndTime(endTime);
        event.setMaxCoordinators(maxCoordinators); event.setMaxLogistics(maxLogistics);
        event.setMaxLogisticsPerCoordinator(maxLogisticsPerCoordinator);
        eventRepository.updated(event);
    }

    public List<Event> getEventsByArrayIds(List<Integer> ids) {
        List<Event> events = new ArrayList<>();
        for (int id : ids) {
            events.add(eventRepository.getById(id));
        }
        return events;
    }

    public void deleteEventById(int id) {
        assignmentRepository.deleteByEvent(id);
        eventRepository.delete(id);
    }


    private void validateLimits(int start, int end, int maxCoord, int maxLog, int maxPerCoord) {
        if (start < 0 || end > 24 || end <= start)
            throw new BusinessException("La hora de fin debe ser mayor que la de inicio.");
        if (maxCoord < 1 || maxLog < 1 || maxPerCoord < 1)
            throw new BusinessException("Los límites deben ser al menos 1.");
        if (maxPerCoord > maxLog)
            throw new BusinessException("El máximo por coordinador no puede superar el máximo de logísticos.");
    }
}
