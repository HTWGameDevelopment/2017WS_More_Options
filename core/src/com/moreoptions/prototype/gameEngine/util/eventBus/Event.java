package com.moreoptions.prototype.gameEngine.util.eventBus;

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
        return data.cast(eventData.get(identifier));
    }


    public String getIdentifier() {
        return identifier;
    }


    public void setData(HashMap<String,Object> data) {
        this.eventData = data;
    }

    public HashMap<String, Object> getDatas() {

        return eventData;
    }
}
