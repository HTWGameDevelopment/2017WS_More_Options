package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.Item;
import com.moreoptions.prototype.gameEngine.data.ItemDatabase;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.data.enemy.Enemy;
import com.moreoptions.prototype.gameEngine.util.EnemyFactory;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventBus;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventSubscriber;

import java.util.ArrayList;
import java.util.Random;

public class LootSystem extends EntitySystem{


    private Family enemyFamily = Family.all(EnemyComponent.class).get();
    private Family playerFamily = Family.all(PlayerComponent.class).get();

    private ComponentMapper<LootComponent> lootMapper = ComponentMapper.getFor(LootComponent.class);
    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<EnemyComponent> enMapper = ComponentMapper.getFor(EnemyComponent.class);
    private ItemDatabase idata = ItemDatabase.getInstance();




    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        ImmutableArray<Entity> enemies = getEngine().getEntitiesFor(enemyFamily);
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(playerFamily);

        for (Entity enemy : enemies) {

            LootComponent lc = lootMapper.get(enemy);
            EnemyComponent ec = enMapper.get(enemy);
            PositionComponent pc = posMapper.get(enemy);
            Random r = new Random();

            if(ec.isDead()) {
                for (int i = 0; i < lc.getLootList().size(); i++) {
                    Item it = lc.getLootList().get(i);
                    if (r.nextInt(100) >= it.getChance()) {
                        Entity item = idata.getItemEntity(it.getName(), GameWorld.getInstance().getRoomManager().getCurrentRoom(), pc.getX(), pc.getY());
                        getEngine().addEntity(item);
                    }
                }
            }
        }
    }

}
