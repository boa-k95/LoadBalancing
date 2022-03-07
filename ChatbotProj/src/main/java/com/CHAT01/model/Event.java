package com.CHAT01.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
public class Event {
    private String eventName;
    private Map<String ,? super Object> eventParameters;

    public Event(String eventName){
        this.eventName = eventName;
        this.eventParameters = new HashMap<>();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return getEventName().equals(event.getEventName()) && Objects.equals(getEventParameters(), event.getEventParameters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEventName(), getEventParameters());
    }

    public void propagateEventParameters(Map<String, ? super Object> oldEventParameters) {
        for (Map.Entry<String, ? super Object> oldEntry : oldEventParameters.entrySet()) {
            eventParameters.putIfAbsent(oldEntry.getKey(), oldEntry.getValue());
        }
    }
}
