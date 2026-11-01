package com.desgroup.logic;

import java.time.LocalDate;
import com.desgroup.interfaces.rulesInterfaces.IEventValidator;

import com.desgroup.utils.BusinessException;

public class EventValidator implements IEventValidator {
    @Override
    public void validate(String name, LocalDate date, int startTime, int endTime,
            String country, String city, String placeName, String address, int capacity,
            int maxCoordinators, int maxLogistics, int maxLogisticsPerCoordinator) {

        requireNotBlank(name, "nombre");
        requireNotBlank(country, "país");
        requireNotBlank(city, "ciudad");
        requireNotBlank(placeName, "lugar");
        requireNotBlank(address, "dirección");
        if (date == null)
            throw new BusinessException("La fecha es obligatoria.");

        if (startTime < 0 || startTime > 23)
            throw new BusinessException("La hora debe ser un número entero entre 0 y 23.");
        if (endTime < 1 || endTime > 24)
            throw new BusinessException("La hora de fin debe ser un número entero entre 1 y 24.");
        if (endTime <= startTime)
            throw new BusinessException("La hora de fin debe ser mayor que la de inicio.");
        if (capacity <= 0)
            throw new BusinessException("La capacidad debe ser un número entero mayor que 0.");

        if (maxCoordinators < 1 || maxLogistics < 1 || maxLogisticsPerCoordinator < 1)
            throw new BusinessException("Los límites deben ser al menos 1.");
        if (maxLogisticsPerCoordinator > maxLogistics)
            throw new BusinessException("El máximo por coordinador no puede superar el máximo de logísticos.");
    }

    private void requireNotBlank(String value, String fieldLabel) {
        if (value == null || value.isBlank())
            throw new BusinessException("El campo '" + fieldLabel + "' es obligatorio.");
    }
}
