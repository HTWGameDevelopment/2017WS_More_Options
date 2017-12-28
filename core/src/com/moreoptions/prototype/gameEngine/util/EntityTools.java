package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.moreoptions.prototype.gameEngine.components.CircleCollisionComponent;
import com.moreoptions.prototype.gameEngine.components.SquareCollisionComponent;

/**
 * Tools that shouldnt exist :(
 */
public class EntityTools {

    static ComponentMapper<CircleCollisionComponent> ccMapper = ComponentMapper.getFor(CircleCollisionComponent.class);
    static ComponentMapper<SquareCollisionComponent> sqcMapper = ComponentMapper.getFor(SquareCollisionComponent.class);

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

    public static Rectangle getBoundingRectangle(Entity entity) throws EntityToolsException {

        if(sqcMapper.has(entity)) {
            return sqcMapper.get(entity).getHitbox();
        } else if(ccMapper.has(entity)) {
            Circle c = ccMapper.get(entity).getHitbox();
            return new Rectangle(c.x -c.radius, c.y - c.radius, c.radius *2, c.radius* 2);
        } else throw new EntityToolsException("Tried to fetch BoundRectangle of an Entity without any collision");

    }
}
