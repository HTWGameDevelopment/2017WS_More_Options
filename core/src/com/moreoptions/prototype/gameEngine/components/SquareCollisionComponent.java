package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by denwe on 06.11.2017.
 */
public class SquareCollisionComponent implements Component {

    private Rectangle square;

    public SquareCollisionComponent(float x, float y, float size) {
        this.square = new Rectangle(x,y,size,size);
    }

    public void update(PositionComponent p) {
        square.setPosition(p.getX(),p.getY());
    }

    public Rectangle getHitbox() {
        return square;
    }

}
