package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by denwe on 30.10.2017.
 */
public class CollisionUtil {

    public static float getXOverlap(Circle c, Rectangle r, float oldX, float oldY) {

        //First check if theres an overlap. If not, overlap is zero.
        if(!Intersector.overlaps(c,r)) return 0;
        //First check if were approaching from left or from right!
        boolean left = (oldX < r.x) ? true : false;
        //Then check if center is between bounds
        if(left && ((c.y <= (r.y + r.height) && c.y >= r.y))) return -(c.x - r.x + c.radius);
        if(!left && ((c.y <= (r.y + r.height) && c.y >= r.y))) return Math.abs(r.x + r.width - c.x + c.radius);
        //Then check if the maximum correction is needed
        if(left && (c.x > (r.x+c.radius))) return -(c.x - r.x + c.radius);
        if(!left && (c.x < r.x + r.width - c.radius)) return Math.abs(r.x + r.width - c.x + c.radius);

        //Then check the nearest upper or lower bound ( left one if approaching from left, right one if approaching from right).
        //Take the intersection furthest away and correct the distance between bound.x to intersection + radius into the direction were coming from.
        //To check this we have to find out if were coming from top or bot

        boolean top = oldY > r.y+r.height;

        if(top) {
            float startA = r.x;
            float startB = r.y+r.height;
            float endA = r.x+r.width;
            float endB = r.y+r.height;

            List<Vector2> intersections = getCircleLineIntersectionPoint(startA,startB,endA,endB,c.x,c.y,c.radius);

            //TWO points of intersection, because intersect a line and a circle

            float pointA = intersections.get(0).x;
            float pointB = intersections.get(1).x;

            if(left) {
                float d1 = pointA - r.x;
                float d2 = pointB - r.x;
                return (d1>d2) ? d1 : -d2;
            } else {
                float d1 = pointA - r.x-r.width;
                float d2 = pointB - r.x-r.width;
                return (d1<d2) ? -d1 : d2;
            }
        } else {
            float startA = r.x;
            float startB = r.y;
            float endA = r.x+r.width;
            float endB = r.y;

            //TWO points of intersection, because intersect a line and a circle
            List<Vector2> intersections = getCircleLineIntersectionPoint(startA,startB,endA,endB,c.x,c.y,c.radius);

            float pointA = intersections.get(0).x;
            float pointB = intersections.get(1).x;

            if(left) {
                float d1 = pointA - r.x;
                float d2 = pointB - r.x;
                return (d1>d2) ? d1 : -d2;
            } else {
                float d1 = pointA - r.x-r.width;
                float d2 = pointB - r.x-r.width;
                return (d1<d2) ? -d1 : d2;
            }
        }
    }


    public static float getYOverlap(Circle c, Rectangle r, float oldX, float oldY) {

        //First check if theres an overlap. If not, overlap is zero.
        if(!Intersector.overlaps(c,r)) return 0;
        //First check if were approaching from top or from bot!
        boolean bot = (oldY < r.y) ? true : false;
        //Then check if center is between bounds
        if(bot && ((c.x <= (r.x + r.width) && c.x >= r.x))) return -(c.y - r.y + c.radius);
        if(!bot && ((c.x <= (r.x + r.width) && c.x >= r.x))) return Math.abs(r.y + r.height - c.y + c.radius);
        //Then check if the maximum correction is needed
        if(bot && (c.y > r.y+c.radius)) return -(c.y - r.y + c.radius);
        if(!bot && (c.y < r.y + (r.height - c.radius))) return Math.abs(r.y + r.height - c.y + c.radius);

        //Then check the nearest upper or lower bound ( left one if approaching from left, right one if approaching from right).
        //Take the intersection furthest away and correct the distance between bound.x to intersection + radius into the direction were coming from.
        //To check this we have to find out if were coming from top or bot

        boolean left = oldX < r.x;

        if(left) {
            float startA = r.x;
            float startB = r.y;
            float endA = r.x;
            float endB = r.y+r.height;

            List<Vector2> intersections = getCircleLineIntersectionPoint(startA,startB,endA,endB,c.x,c.y,c.radius);

            //TWO points of intersection, because intersect a line and a circle

            float pointA = intersections.get(0).y;
            float pointB = intersections.get(1).y;

            if(bot) {
                float d1 = pointA - r.y;
                float d2 = pointB - r.y;
                return (d1>d2) ? d1 : -d2;
            } else {
                float d1 = pointA - r.y-r.height;
                float d2 = pointB - r.y-r.height;
                return (d1<d2) ? -d1 : d2;
            }
        } else {
            float startA = r.x+r.width;
            float startB = r.y;
            float endA = r.x+r.width;
            float endB = r.y+r.height;


            //TWO points of intersection, because intersect a line and a circle
            List<Vector2> intersections = getCircleLineIntersectionPoint(startA,startB,endA,endB,c.x,c.y,c.radius);

            if(intersections.isEmpty()) return 0;

            float pointA = intersections.get(0).y;
            float pointB = intersections.get(1).y;

            if(bot) {
                float d1 = pointA - r.y;
                float d2 = pointB - r.y;
                return (d1>d2) ? d1 : -d2;
            } else {
                float d1 = pointA - r.y-r.height;
                float d2 = pointB - r.y-r.height;
                return (d1<d2) ? -d1 : d2;
            }
        }
        //return 0;
    }

    private static List<Vector2> getCircleLineIntersectionPoint(float startA, float startB, float endA, float endB, float x, float y, float radius) {
        float baX = endA - startA;
        float baY = endB - startB;
        float caX = x - startA;
        float caY = y - startB;

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

        Vector2 p1 = new Vector2(startA - baX * abScalingFactor1, startB
                - baY * abScalingFactor1);

        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return Collections.singletonList(p1);
        }
        Vector2 p2 = new Vector2(startA - baX * abScalingFactor2, startB
                - baY * abScalingFactor2);
        return Arrays.asList(p1, p2);
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

    public static boolean intersectSegmentRectangle(float x1,float y1, float x2, float y2, Rectangle rect) {

        if(Intersector.intersectSegments(x1,y1,x2,y2,rect.x,rect.y,rect.x+rect.width,rect.y,null)) return true;
        if(Intersector.intersectSegments(x1,y1,x2,y2,rect.x,rect.y,rect.x+rect.width,rect.y+rect.height,null)) return true;
        if(Intersector.intersectSegments(x1,y1,x2,y2,rect.x,rect.y,rect.x,rect.y+rect.getHeight(),null)) return true;
        if(Intersector.intersectSegments(x1,y1,x2,y2,rect.x+rect.width,rect.y+rect.height,rect.x+rect.width,rect.y,null)) return true;

        return false;
    }


}
