package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.moreoptions.prototype.gameEngine.components.CircleCollisionComponent;
import com.moreoptions.prototype.gameEngine.components.SquareCollisionComponent;

/**
 * Tools that shouldnt exist :(
 */
public class EntityTools {

    public static Rectangle getSquareHitbox(Entity e) throws Exception {
        SquareCollisionComponent s = e.getComponent(SquareCollisionComponent.class);
        return s.getHitbox();
    }

    public static Circle getCircleHitbox(Entity e) throws Exception {
        CircleCollisionComponent s = e.getComponent(CircleCollisionComponent.class);
        return s.getHitbox();
    }

    public static void resetEnemy(Entity e) {
        //TODO impl
    }

    public static void resetDestructible(Entity e) {
        //Todo impl
    }
}
