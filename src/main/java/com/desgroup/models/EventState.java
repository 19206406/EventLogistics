package com.desgroup.models;

public enum EventState {
    PROGRAMADO("Programado", false),
    EN_CURSO("En curso", false),
    FINALIZADO("Finalizado", true),
    CANCELADO("Cancelado", true);

    private final String label;
    private final boolean closed;

    private EventState(String label, boolean closed) {
        this.label = label;
        this.closed = closed;
    }

    public String getLabel() {
        return label;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public String toString() {
        return label;
    }

    public static EventState fromLabel(String label) {
        for (EventState state : values()) {
            if (state.label.equals(label))
                return state;
        }
        throw new IllegalArgumentException("Estado del evento desconocido: " + label);
    }
}
