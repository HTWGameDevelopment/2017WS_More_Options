package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.moreoptions.prototype.gameEngine.components.CollisionComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import javafx.geometry.Pos;

/**
 * Created by denwe on 31.10.2017.
 */
public class EntityTools {



    public static Rectangle getTileHitbox(Entity e) throws Exception {
        CollisionComponent c = e.getComponent(CollisionComponent.class);
        if(c.getShape() != CollisionComponent.Shape.RECTANGLE) throw new Exception("Not a rectangle hitbox");
        PositionComponent p = e.getComponent(PositionComponent.class);

        return new Rectangle(p.getX(),p.getY(),c.getSize(),c.getSize());
    }

    public static Circle getEntityHitbox(Entity e) throws Exception {
        CollisionComponent c = e.getComponent(CollisionComponent.class);
        if(c.getShape() != CollisionComponent.Shape.CIRCLE) throw new Exception("Not a circle hitbox");
        PositionComponent p = e.getComponent(PositionComponent.class);

        return new Circle(p.getX(),p.getY(),c.getSize());
    }
}
