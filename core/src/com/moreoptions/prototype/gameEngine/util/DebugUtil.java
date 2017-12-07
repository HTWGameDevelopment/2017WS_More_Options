package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.DebugCircleComponent;
import com.moreoptions.prototype.gameEngine.components.DebugLineComponent;
import com.moreoptions.prototype.gameEngine.components.TimedComponent;

/**
 * Created by denwe on 04.11.2017.
 */
public class DebugUtil {

    private void createDebugLine(Vector2 start, Vector2 end, float x) {
        Entity e = new Entity();
        e.add(new DebugLineComponent(start,end));
        e.add(new TimedComponent(x));
        GameWorld.getInstance().addEntity(e);
    }
/*
    private void createDebugRing(Vector2 center, float radius, float x) {
        Entity e = new Entity();
        e.add(new DebugCircleComponent(center.x, center.y,radius));
        e.add(new TimedComponent(x));
        GameWorld.getInstance().addEntity(e);
    }*/
}
