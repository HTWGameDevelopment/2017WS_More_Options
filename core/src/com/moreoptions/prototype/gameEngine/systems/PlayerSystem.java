package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.moreoptions.prototype.gameEngine.components.PlayerComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.StatsComponent;

/**
 * Created by Dennis on 06.12.2017.
 */
public class PlayerSystem extends EntitySystem{

    private Family f = Family.all(PlayerComponent.class).get();

    private ComponentMapper<StatsComponent> scMapper = ComponentMapper.getFor(StatsComponent.class);

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        ImmutableArray<Entity> players = getEngine().getEntitiesFor(f);

        for(Entity p : players) {

           float x =  scMapper.get(p).getStats().getCurrentShotCooldown();
            scMapper.get(p).getStats().setCurrentShotCooldown(x+deltaTime);
        }

    }
}