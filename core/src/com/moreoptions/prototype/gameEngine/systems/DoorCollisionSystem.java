package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.moreoptions.prototype.gameEngine.components.CircleCollisionComponent;
import com.moreoptions.prototype.gameEngine.components.CollisionComponent;
import com.moreoptions.prototype.gameEngine.components.DoorComponent;
import com.moreoptions.prototype.gameEngine.components.PlayerComponent;

/**
 * Created by denwe on 26.11.2017.
 */
public class DoorCollisionSystem extends EntitySystem {


    Family playersF = Family.all(PlayerComponent.class).get();
    Family doorsF = Family.all(DoorComponent.class).get();

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        ImmutableArray<Entity> doors = getEngine().getEntitiesFor(doorsF);
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(playersF);

        for(Entity player : players) {
            for(Entity door : doors) {
                CircleCollisionComponent pc = player.getComponent(CircleCollisionComponent.class);
                CircleCollisionComponent doorcc = door.getComponent(CircleCollisionComponent.class);
                if(pc.getHitbox().overlaps(doorcc.getHitbox())) {
                    door.getComponent(CollisionComponent.class).getOnCollision().onCollision(player,door);
                }
            }
        }
    }
}
