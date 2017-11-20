package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.moreoptions.prototype.gameEngine.components.*;

/**
 * System that handles pickupcomponents
 */
public class PickupSystem extends EntitySystem {

    private Family f = Family.all(PickupComponent.class, PositionComponent.class, CollisionComponent.class, CircleCollisionComponent.class).get();
    private Family p = Family.all(PlayerComponent.class).get();

    private ComponentMapper<CircleCollisionComponent> cmapper = ComponentMapper.getFor(CircleCollisionComponent.class);

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        ImmutableArray<Entity> pickups = getEngine().getEntitiesFor(f);
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(p);

        for(Entity p : players) {
            for(Entity pickup : pickups) {
                if(cmapper.get(p).getHitbox().overlaps(cmapper.get(pickup).getHitbox())) {
                    if(pickup.getComponent(PickupComponent.class).trigger(p)) getEngine().removeEntity(pickup);
                }
            }
        }

    }
}
