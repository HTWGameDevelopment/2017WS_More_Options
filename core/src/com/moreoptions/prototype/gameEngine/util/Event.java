package com.moreoptions.prototype.gameEngine.util;

import java.util.HashMap;

public class Event {

    private String identifier;
    private HashMap<String, Object> eventData = new HashMap<String, Object>();

    public Event(String identifier) {
        this.identifier = identifier;
    }

    public void addData(String identifier , Object o) {
        eventData.put(identifier,o);
    }

    public <T> T getData(String identifier, Class<T> data) {
        T t = data.cast(eventData.get(identifier));
        return t;
    }


    public String getIdentifier() {
        return identifier;
    }
}
