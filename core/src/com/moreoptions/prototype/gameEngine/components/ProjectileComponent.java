package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by User on 11/20/2017.
 */
public class ProjectileComponent implements Component {

    float range = 0;
    float distanceTravelled = 0;


    public ProjectileComponent(float range) {
        this.range = range;
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
}
