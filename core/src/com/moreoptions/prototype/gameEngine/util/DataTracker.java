package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventBus;

import java.util.HashMap;

/**
 * Created by Dennis on 03.01.2018.
 */
public class DataTracker {



    public static void trackIntData(String id, float data) {

        Event e = new Event(Consts.ACHIEVEMENT_EVENT_ID);
        e.addData("id", id);
        e.addData(Consts.FLOAT, data);
        EventBus.getInstance().addEvent(e);

    }

    public static void trackData(HashMap<String, Object> data) {

        Event e = new Event(Consts.ACHIEVEMENT_EVENT_ID);
        e.setData(data);
        EventBus.getInstance().addEvent(e);

    }

    public static void trackFloatData(String id, float i) {

        Event e = new Event(Consts.ACHIEVEMENT_EVENT_ID);
        e.addData("id", id);
        e.addData(Consts.FLOAT, i);

        EventBus.getInstance().addEvent(e);


    }

    public static void trackEnemyKills(String id, Entity e) {
        Event ev = new Event(Consts.ACHIEVEMENT_EVENT_ID);
        ev.addData("id", id);
        ev.addData(Consts.ENEMY_DATA, e);

        EventBus.getInstance().addEvent(ev);
    }
}
