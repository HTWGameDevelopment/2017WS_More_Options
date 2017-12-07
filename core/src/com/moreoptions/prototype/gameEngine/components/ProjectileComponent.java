package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.moreoptions.prototype.gameEngine.data.callback.HitEvent;

/**
 * Created by User on 11/20/2017.
 */
public class ProjectileComponent implements Component {

    float range = 0;
    float distanceTravelled = 0;
    float dmg = 0;



    HitEvent onHit;


    public ProjectileComponent(float damage, float range) {
        this.range = range;
        this.dmg = damage;
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
}
