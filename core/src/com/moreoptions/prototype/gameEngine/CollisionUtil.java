package com.moreoptions.prototype.gameEngine;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.moreoptions.prototype.gameEngine.components.CollisionComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;

/**
 * Created by denwe on 30.10.2017.
 */
public class CollisionUtil {

    public static float getXOverlap(Circle c, Rectangle r) {
        float circleX = c.x;
        float rectangleX = r.x;

        float radius = c.radius;

        if(Intersector.overlaps(c,r))
            return (rectangleX - circleX) + radius;
        return 0;
    }


    public static float getYOverlap(Circle c, Rectangle r) {
        float circleY = c.y;
        float rectangleY = r.y;

        float radius = c.radius;

        if(Intersector.overlaps(c,r))
            return (rectangleY - circleY) + radius;
        return 0;
    }


}
