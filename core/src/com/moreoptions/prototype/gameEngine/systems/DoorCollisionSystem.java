package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.util.DataTracker;

/**
 * Created by denwe on 26.11.2017.
 */
public class DoorCollisionSystem extends EntitySystem {

    private Family playersF = Family.all(PlayerComponent.class).get();
    private Family doorsF = Family.all(DoorComponent.class).get();

    private ComponentMapper<CircleCollisionComponent> cccMapper = ComponentMapper.getFor(CircleCollisionComponent.class);
    private ComponentMapper<SquareCollisionComponent> sqcMapper = ComponentMapper.getFor(SquareCollisionComponent.class);
    private ComponentMapper<DoorComponent> dcMapper = ComponentMapper.getFor(DoorComponent.class);
    private ComponentMapper<CollisionComponent> ccMapper = ComponentMapper.getFor(CollisionComponent.class);


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        ImmutableArray<Entity> doors = getEngine().getEntitiesFor(doorsF);
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(playersF);

        for(Entity player : players) {
            for(Entity door : doors) {
                CircleCollisionComponent pc = cccMapper.get(player);
                SquareCollisionComponent doorsqc = sqcMapper.get(door);
                DoorComponent doorComponent = dcMapper.get(door);
                CollisionComponent doorCollisionComponent = ccMapper.get(door);

                if(Intersector.overlaps(pc.getHitbox(), doorsqc.getHitbox()) && doorComponent.isOpen()) {
                    doorCollisionComponent.getOnCollision().onCollision(player,door);

                    //Create Achivement door, add to EventBus
                    DataTracker.trackIntData(Consts.DOOR_STAT, 1);

                }
            }
        }
    }
}
