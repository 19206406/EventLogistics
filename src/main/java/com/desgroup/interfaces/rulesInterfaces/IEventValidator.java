package com.desgroup.interfaces.rulesInterfaces;

import java.time.LocalDate;

public interface IEventValidator {
    void validate(String name, LocalDate date, int startTime, int endTime,
            String country, String city, String placeName, String address, int capacity,
            int maxCoordinators, int maxLogistics, int maxLogisticsPerCoordinator);
}
