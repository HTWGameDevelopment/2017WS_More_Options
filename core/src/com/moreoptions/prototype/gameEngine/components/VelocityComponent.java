package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Component that holds information pertaining to acceleration / deceleration of entities.
 */
public class VelocityComponent implements Component{

    private Vector2 velocity = new Vector2();

    private float speed;
    private float deceleration;

    public VelocityComponent() {

    }


    public VelocityComponent(float speed, float deceleration) {
        this.speed = speed;
        this.deceleration = deceleration;
    }

    public float getVelX() {
        return velocity.x;
    }

    public void setVelX(float velX) {
        this.velocity.x = velX;
    }

    public float getVelY() {
        return velocity.y;
    }

    public void setVelY(float velY) {
        this.velocity.y = velY;
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

    public Vector2 getVelocity() {
        return velocity;
    }
}
