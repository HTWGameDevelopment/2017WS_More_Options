package com.moreoptions.prototype.gameEngine.util.eventBus;

import java.util.HashMap;

public class EventSubscriber {

    private HashMap<String, EventListener> eventListenerMap = new HashMap<String, EventListener>();


    public void subscribe(String identifier, EventListener eventListener) {

        if(eventListenerMap.containsKey(identifier)) try {
            throw new EventSubscriberException(identifier);
        } catch (EventSubscriberException e) {
            e.printStackTrace();
        }
        eventListenerMap.put(identifier, eventListener);
        EventBus.getInstance().addSubscriber(identifier, eventListener);
        System.out.println("Subscriber registered for:" + identifier);
    }

    private class EventSubscriberException extends Throwable {
        public EventSubscriberException(String identifier) {
            super("This subscriber already listens to " + identifier);
        }

    }
}
