package com.moreoptions.prototype.gameEngine.data.callback;

import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.GameWorld;

public interface CollisionEvent {

    boolean onCollision(Entity us, Entity them);

    class DefaultCollisionEvent implements CollisionEvent {
        @Override
        public boolean onCollision(Entity us, Entity them) {
            return true;
        }
    }

    class DefaultProjectileCollisionEvent implements CollisionEvent {

        @Override
        public boolean onCollision(Entity us, Entity them) {
            GameWorld.getInstance().removeEntity(us);
            return true;
        }
    }
}
