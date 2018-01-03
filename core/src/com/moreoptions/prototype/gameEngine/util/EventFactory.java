package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventBus;

public class EventFactory {


    public static void createShot(Entity self, Vector2 dir) {

        Event e = new Event(Consts.SHOOT_EVENT);
        e.addData(Consts.ENTITY, self);
        e.addData(Consts.DIRECTION, dir);
        EventBus.getInstance().addEvent(e);

    }
}
