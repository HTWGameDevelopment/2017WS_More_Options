package com.moreoptions.prototype.gameEngine.data.callback;

import com.badlogic.ashley.core.Entity;

/**
 * Created by User on 11/20/2017.
 */
public interface CollisionEvent {

    boolean onCollision(Entity us, Entity them);

    class DefaultCollisionEvent implements CollisionEvent {
        @Override
        public boolean onCollision(Entity us, Entity them) {
            return true;
        }
    }
}
