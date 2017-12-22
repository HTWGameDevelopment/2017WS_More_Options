package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.data.callback.CollisionEvent;

/**
 * Component that holds old position information for collision
 */
public class CollisionComponent implements Component{

    private Vector2 oldPosition = new Vector2();

    private CollisionEvent event;


    public CollisionComponent() {
        event = new CollisionEvent.DefaultCollisionEvent();
    }


    public CollisionComponent(CollisionEvent event) {
        this.event = event;
    }


    public void setOldX(float oldX) {
        this.oldPosition.x = oldX;
    }

    public void setOldY(float oldY) {
        this.oldPosition.y = oldY;
    }

    public float getOldX() {
        return this.oldPosition.x;
    }

    public float getOldY() {
        return this.oldPosition.y;
    }

    public Vector2 getOldPosition() {
        return oldPosition;
    }

    public CollisionEvent getOnCollision() {
        return event;
    }
}
