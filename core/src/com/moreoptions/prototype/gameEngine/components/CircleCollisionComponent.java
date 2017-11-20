package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Circle;

/**
 * Created by denwe on 06.11.2017.
 */
public class CircleCollisionComponent implements Component {

    private Circle hitbox;

    public CircleCollisionComponent(float x, float y, float radius) {
        this.hitbox = new Circle(x,y,radius);
    }

    public void update(PositionComponent p) {
        hitbox.setPosition(p.getX(),p.getY());
    }

    public Circle getHitbox() {
        return hitbox;
    }

}
