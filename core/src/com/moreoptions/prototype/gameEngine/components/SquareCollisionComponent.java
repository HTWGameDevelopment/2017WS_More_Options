package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by denwe on 06.11.2017.
 */
public class SquareCollisionComponent implements Component {

    private Rectangle square;

    public SquareCollisionComponent(float x, float y, float size) {
        square = new Rectangle(x,y,size,size);
    }

    public void update(float x, float y) {
        square.setPosition(x,y);
    }

    public Rectangle getHitbox() {
        return square;
    }

}
