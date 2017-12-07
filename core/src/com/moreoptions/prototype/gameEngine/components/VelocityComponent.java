package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;

/**
 * Component that holds information pertaining to acceleration / deceleration of entities.
 */
public class VelocityComponent implements Component{

    private float velX, velY;
    private float speed;
    private float deceleration;

    /*


    TODO 3. Milestone:

    velX, velY Vector!

    Constructor speed und direction übergeben!

     */


    public VelocityComponent(float speed, float deceleration) {
        this.speed = speed;
        this.deceleration = deceleration;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDeceleration() {
        return deceleration;
    }
}
