package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.moreoptions.prototype.gameEngine.components.*;

/**
 * Created by denwe on 06.11.2017.
 */
public class PickupSystem extends EntitySystem {

    Family f = Family.all(PickupComponent.class, PositionComponent.class, CollisionComponent.class, CircleCollisionComponent.class).get();
    Family p = Family.all(PlayerComponent.class).get();

    ComponentMapper<CircleCollisionComponent> cmapper = ComponentMapper.getFor(CircleCollisionComponent.class);

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        ImmutableArray<Entity> pickups = getEngine().getEntitiesFor(f);
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(p);

        for(Entity p : players) {
            for(Entity pickup : pickups) {
                if(cmapper.get(p).getHitbox().overlaps(cmapper.get(pickup).getHitbox())) {
                    pickup.getComponent(PickupComponent.class).trigger(p);
                }
            }
        }

    }
}
