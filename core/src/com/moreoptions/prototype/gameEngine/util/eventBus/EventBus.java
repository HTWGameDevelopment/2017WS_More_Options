package com.moreoptions.prototype.gameEngine.util.eventBus;

import java.util.ArrayList;
import java.util.HashMap;

public class EventBus {
    private static EventBus ourInstance = new EventBus();

    public static EventBus getInstance() {
        return ourInstance;
    }

    private HashMap<String, ArrayList<EventListener>> subscriberMap = new HashMap<String, ArrayList<EventListener>>();

    private EventBus() {

    }

    public void addEvent(Event event) {
        String identifier = event.getIdentifier();

        for(String key : subscriberMap.keySet()) {
            if(key.equals(identifier)) {
                ArrayList<EventListener> subscriberList = subscriberMap.get(key);

                for(EventListener es : subscriberList) {
                    es.trigger(event);
                }
            }
        }
    }

    public void addSubscriber(String identifier, EventListener eventListener) {
        if(subscriberMap.containsKey(identifier)) {
            subscriberMap.get(identifier).add(eventListener);
        } else {
            ArrayList<EventListener> eventListeners = new ArrayList<EventListener>();
            eventListeners.add(eventListener);
            subscriberMap.put(identifier, eventListeners);
        }
    }

    public void clear() {
        subscriberMap.clear();
    }
}
