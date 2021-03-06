package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.EnemyComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.StatsComponent;
import com.moreoptions.prototype.gameEngine.data.*;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventListener;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventSubscriber;
import com.sun.xml.internal.ws.dump.LoggingDumpTube;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Dennis on 06.12.2017.
 */
public class EnemySystem extends EntitySystem{

    private Family enemyFamily = Family.all(EnemyComponent.class).get();

    private ComponentMapper<EnemyComponent> ecMapper = ComponentMapper.getFor(EnemyComponent.class);
    private ComponentMapper<StatsComponent> statMapper = ComponentMapper.getFor(StatsComponent.class);

    EventSubscriber enemySpawner = new EventSubscriber();

    public EnemySystem() {
        enemySpawner.subscribe(Consts.SPAWN_ENEMY, new EventListener() {
            @Override
            public boolean trigger(Event e) {

                HashMap<String, Object> map = e.getDatas();
                for(Object o : map.values()) {
                    getEngine().addEntity((Entity) o);

                }
                return true;
            }
        });




    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        ImmutableArray<Entity> enemies = getEngine().getEntitiesFor(enemyFamily);
        for (Entity e : enemies){
            StatsComponent sc = statMapper.get(e);
            Statistics stats = sc.getStats();
            EnemyComponent ec = ecMapper.get(e);

            if(stats.getCurrentHealth()<= 0) {
                Entity itemDrop = generateItemOnDeath(e, ec.getRoom());
                ec.setDead(true);
                if(ec.getOnDeath() != null) {
                    ec.getOnDeath().onTrigger(e, null);
                    if (itemDrop != null) GameWorld.getInstance().addEntity(itemDrop);
                }

                ec.getRoom().checkForClear();
                GameWorld.getInstance().removeEntity(e);
            } else {
                stats.setCurrentShotCooldown(stats.getCurrentShotCooldown() + deltaTime);
            }
        }
    }

    private Entity generateItemOnDeath(Entity e, Room room) {
        Random random = new Random();
        float percentage = random.nextFloat();

        if (percentage >= 0.99) {
            PositionComponent epc = e.getComponent(PositionComponent.class);
            return ItemDatabase.getInstance().generateSpecialItem(epc.getX(), epc.getY(), room);
        } else if (percentage >= 0.9) {
            PositionComponent epc = e.getComponent(PositionComponent.class);
            return ItemDatabase.getInstance().generateItem(room, epc.getX(), epc.getY());
        } else if (percentage >= 0.33) {
            PositionComponent epc = e.getComponent(PositionComponent.class);
            return ItemDatabase.getInstance().generateGold(room, epc.getX(), epc.getY());
        } else {
            return null;
        }
    }
}
