package com.moreoptions.prototype.gameEngine.util;

import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventBus;

import java.util.HashMap;

/**
 * Created by Dennis on 03.01.2018.
 */
public class DataTracker {



    public static void trackIntData(String id, int data) {

        Event e = new Event(Consts.ACHIEVEMENT_EVENT_ID);
        e.addData("id", id);
        e.addData(Consts.INT, data);
        EventBus.getInstance().addEvent(e);

        System.out.println("Tracking data, creating event");

    }

    public static void trackData(HashMap<String, Object> data) {

        Event e = new Event(Consts.ACHIEVEMENT_EVENT_ID);
        e.setData(data);
        EventBus.getInstance().addEvent(e);

    }
}
