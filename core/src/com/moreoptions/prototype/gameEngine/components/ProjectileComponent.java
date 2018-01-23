package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.data.callback.HitEvent;

/**
 * Created by User on 11/20/2017.
 */
public class ProjectileComponent implements Component {

    float range = 0;
    float distanceTravelled = 0;
    float dmg = 0;
    boolean isEnemy;

    Entity owner;

    HitEvent onHit;

    public ProjectileComponent(Entity owner,float damage, float range, boolean b) {
        this.owner = owner;
        this.range = range;
        this.dmg = damage;
        this.isEnemy = b;
        onHit = new HitEvent.StandardHitEvent();
    }



    public float getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(float distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public float getRange() {
        return range;
    }

    public float getDmg() {
        return dmg;
    }

    public HitEvent getHitEvent() {
        return onHit;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }

    public void setHitEvent(HitEvent onHitEvent) {
        this.onHit = onHitEvent;
    }

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }
}
