package com.moreoptions.prototype.gameEngine.data.callback;

import com.badlogic.ashley.core.Entity;

public interface CollisionEvent {

    boolean onCollision(Entity us, Entity them);

    class DefaultCollisionEvent implements CollisionEvent {
        @Override
        public boolean onCollision(Entity us, Entity them) {
            return true;
        }
    }
}
