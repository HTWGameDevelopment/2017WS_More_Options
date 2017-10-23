package com.moreoptions.prototype;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;

/**
 * Created by Dennis on 23.10.2017.
 */
public class MovementSystem extends EntitySystem {




    @Override
    public void update(float deltaTime) {

        Family family = Family.all(PositionComponent.class, VelocityComponent.class).get();

        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(family);


        for(Entity e : entities) {
            PositionComponent p = e.getComponent(PositionComponent.class);
            VelocityComponent v = e.getComponent(VelocityComponent.class);

            p.x = p.x + v.velX;
            p.y = p.y + v.velY;


        }







    }
}
