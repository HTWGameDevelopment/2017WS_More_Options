package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.EnemyComponent;

/**
 * Created by Dennis on 06.12.2017.
 */
public class EnemySystem extends EntitySystem{

    private Family enemyFamily = Family.all(EnemyComponent.class).get();

    private ComponentMapper<EnemyComponent> ecMapper = ComponentMapper.getFor(EnemyComponent.class);

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        ImmutableArray<Entity> enemies = getEngine().getEntitiesFor(enemyFamily);
        for (Entity e : enemies){
            EnemyComponent ec = ecMapper.get(e);
            if(ec.getCurrentHealth()<= 0) {
                ec.setDead(true);
                ec.getRoom().checkForClear();
                GameWorld.getInstance().removeEntity(e);
            }
        }
    }

}
