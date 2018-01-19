package com.moreoptions.prototype.gameEngine.data.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Item;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.data.callback.GameEvent;

import java.util.ArrayList;

/**
 * Created by denwe on 09.01.2018.
 */
public class Enemy {


    private Color color,borderColor;                //Which Color is our Enemy?
    private EnemyBehavior behavior;     //State-Machine
    private Statistics stats;
    private ArrayList<Item> lootList;
    private Loot loot;

    private int id;
    private int size, collisionSize;

    private GameEvent onDeathEvent;
    private GameEvent onCollisionEvent;
    private GameEvent onHitEvent;


    public Enemy(Color color, Color borderColor, int size, int displaySize, EnemyBehavior behavior, Statistics stats, GameEvent onDeathEvent, GameEvent onCollisionEvent, GameEvent onHitEvent) {

        this.color = color;
        this.borderColor = borderColor;
        this.collisionSize = size;
        this.size = displaySize;
        this.behavior = behavior;
        this.stats = stats;
        this.onDeathEvent = onDeathEvent;
        this.lootList = loot.getLootById(id);


    }

    public Entity getEntity(float x, float y, Room room) {
        Entity e = new Entity();
        e.add(new PositionComponent(x, y));
        e.add(new CollisionComponent());
        e.add(new CircleCollisionComponent(x,y,collisionSize));
        e.add(new DebugCircleComponent(size));
        e.add(new VelocityComponent());
        e.add(new DebugColorComponent(color));
        e.add(new DisplacableComponent(stats.getPushability()));
        e.add(behavior.getAIComponent());
        e.add(new EnemyHitboxComponent(size));
        e.add(new StatsComponent(stats.cpy()));
        e.add(new LootComponent(lootList));

        EnemyComponent ec = new EnemyComponent(x, y, room, id);
        ec.setOnDeath(onDeathEvent);
        e.add(ec);



        return e;
    }


}
