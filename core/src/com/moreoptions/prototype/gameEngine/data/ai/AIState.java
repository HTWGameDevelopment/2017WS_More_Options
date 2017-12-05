package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moreoptions.prototype.gameEngine.data.Room;

public interface AIState {
    void update(Room room, Entity self);

    void draw(ShapeRenderer renderer);
}
