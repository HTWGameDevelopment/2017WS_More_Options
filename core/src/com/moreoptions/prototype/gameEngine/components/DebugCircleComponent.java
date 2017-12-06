package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import javafx.geometry.Pos;

/**
 * Created by denwe on 03.11.2017.
 */
public class DebugCircleComponent implements Component {

    private float radius;


    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public DebugCircleComponent(float radius) {
        this.radius = radius;
    }

}
