package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.*;
import com.moreoptions.prototype.gameEngine.GameEngine;
import com.moreoptions.prototype.gameEngine.components.CollisionComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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


    public static List<Vector2> getCircleLineIntersectionPoint(Vector2 pointA,
                                                             Vector2 pointB, Vector2 center, float radius) {
        float baX = pointB.x - pointA.x;
        float baY = pointB.y - pointA.y;
        float caX = center.x - pointA.x;
        float caY = center.y - pointA.y;

        float a = baX * baX + baY * baY;
        float bBy2 = baX * caX + baY * caY;
        float c = caX * caX + caY * caY - radius * radius;

        float pBy2 = bBy2 / a;
        float q = c / a;

        float disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return Collections.emptyList();
        }
        // if disc == 0 ... dealt with later
        float tmpSqrt = (float) Math.sqrt(disc);
        float abScalingFactor1 = -pBy2 + tmpSqrt;
        float abScalingFactor2 = -pBy2 - tmpSqrt;

        Vector2 p1 = new Vector2(pointA.x - baX * abScalingFactor1, pointA.y
                - baY * abScalingFactor1);
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return Collections.singletonList(p1);
        }
        Vector2 p2 = new Vector2(pointA.x - baX * abScalingFactor2, pointA.y
                - baY * abScalingFactor2);
        return Arrays.asList(p1, p2);
    }

    public static float getYOverlap(Circle c, Rectangle r) {
        float circleY = c.y;
        float rectangleY = r.y;

        float radius = c.radius;

        if(Intersector.overlaps(c,r))
            return (rectangleY - circleY) + radius;
        return 0;
    }


    public static int getVertexCount(Circle c, Rectangle r) {
        int vertexCount = 0;
        if(c.contains(r.x,r.y)) vertexCount++;
        if(c.contains(r.x + GameEngine.getInstance().getTileSize(),r.y)) vertexCount++;
        if(c.contains(r.x + GameEngine.getInstance().getTileSize(),r.y + GameEngine.getInstance().getTileSize())) vertexCount++;
        if(c.contains(r.x,r.y + GameEngine.getInstance().getTileSize())) vertexCount++;

        return vertexCount;
    }
}
