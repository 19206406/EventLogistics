package com.desgroup.models;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EventStateConverter implements AttributeConverter<EventState, String> {
    @Override
    public String convertToDatabaseColumn(EventState state) {

        if (state == null)
            return null;

        return state.getLabel();
    }

    @Override
    public EventState convertToEntityAttribute(String dbValue) {

        if (dbValue == null)
            return null;

        return EventState.fromLabel(dbValue);
    }
}
