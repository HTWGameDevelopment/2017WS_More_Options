package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;

/**
 * Created by Dennis on 06.12.2017.
 */
public class EnemyHitboxComponent implements Component {

    private Circle circle = new Circle();


    public EnemyHitboxComponent(float size) {

        circle.setRadius(size);

    }

    public Circle getCircle() {
        return circle;
    }


}
