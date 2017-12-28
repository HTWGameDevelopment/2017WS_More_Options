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

    public void setMass(float mass) {
        this.mass = mass;
    }

    public void applyForce(Entity e) {
        Vector2 norm = e.getComponent(VelocityComponent.class).getVelocity().cpy().nor();

        dir = norm.cpy();

        strength = e.getComponent(DisplacableComponent.class).getMass();

        System.out.println(dir);
        System.out.println(strength);

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

    public void applyForce(Vector2 norm, int i) {
        this.dir = norm.scl(i);
    }
}