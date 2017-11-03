package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by denwe on 03.11.2017.
 */
public class DebugCircleComponent implements Component {

    Vector2 center;
    float radius;

    public Vector2 getCenter() {
        return center;
    }

    public void setCenter(Vector2 center) {
        this.center = center;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public DebugCircleComponent(Vector2 center, float radius) {
            this.center = center;
            this.radius = radius;
    }
}
