package com.desgroup.interfaces.rulesInterfaces;

import java.time.LocalDate;

import com.desgroup.models.EventState;

public interface IEventValidator {
    void validate(String name, EventState state, LocalDate date, int startTime, int endTime,
            String country, String city, String placeName, String address, int capacity,
            int maxCoordinators, int maxLogistics, int maxLogisticsPerCoordinator);
}
