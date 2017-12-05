package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.moreoptions.prototype.gameEngine.data.callback.CollisionEvent;

/**
 * Component that holds old position information for collision
 */
public class CollisionComponent implements Component{

    private float oldX;
    private float oldY;

    private CollisionEvent event;


    public CollisionComponent() {
        event = new CollisionEvent.DefaultCollisionEvent();
    }


    public CollisionComponent(CollisionEvent event) {
        this.event = event;
    }


    public void setOldX(float oldX) {
        this.oldX = oldX;
    }

    public void setOldY(float oldY) {
        this.oldY = oldY;
    }

    public float getOldX() {
        return oldX;
    }

    public float getOldY() {
        return oldY;
    }

    public CollisionEvent getOnCollision() {
        return event;
    }
}
