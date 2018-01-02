package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.EnemyComponent;
import com.moreoptions.prototype.gameEngine.components.StatsComponent;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.sun.org.glassfish.external.statistics.Stats;

/**
 * Created by Dennis on 06.12.2017.
 */
public class EnemySystem extends EntitySystem{

    private Family enemyFamily = Family.all(EnemyComponent.class).get();

    private ComponentMapper<EnemyComponent> ecMapper = ComponentMapper.getFor(EnemyComponent.class);
    private ComponentMapper<StatsComponent> statMapper = ComponentMapper.getFor(StatsComponent.class);

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        ImmutableArray<Entity> enemies = getEngine().getEntitiesFor(enemyFamily);
        for (Entity e : enemies){
            StatsComponent sc = statMapper.get(e);
            Statistics stats = sc.getStats();
            EnemyComponent ec = ecMapper.get(e);
            if(stats.getCurrentHealth()<= 0) {
                ec.setDead(true);
                ec.getRoom().checkForClear();
                GameWorld.getInstance().removeEntity(e);
            }
        }
    }

}
