package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;

/**
 * Component that holds old position information for collision
 */
public class CollisionComponent implements Component{

    private float oldX;
    private float oldY;


    public CollisionComponent() {

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

}
