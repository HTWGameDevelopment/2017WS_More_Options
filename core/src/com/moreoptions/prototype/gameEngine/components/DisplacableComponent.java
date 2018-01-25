package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by denwe on 22.12.2017.
 */
public class DisplacableComponent implements Component {

    Vector2 dir = new Vector2();
    float mass = 0;
    float strength = 0;
    private boolean immune;
    private float force;

    public DisplacableComponent(float mass) {
        this.mass = mass;
    };

    public Vector2 getDir() {
        return dir;
    }

    public void setDir(Vector2 dir) {
        this.dir = dir;
    }

    public float getMass() {
        return mass;
    }


    public boolean isImmune() {
        return immune;
    }

    public void setForce(int force) {
        this.force = force;

    }

    public void setImmune(boolean immune) {
        this.immune = immune;
    }

    public void applyForce(Vector2 norm, float f) {
        this.dir = norm.scl(f);
    }
}
