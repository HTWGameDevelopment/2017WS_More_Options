package com.moreoptions.prototype.gameEngine.data.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.data.callback.GameEvent;

/**
 * Created by denwe on 09.01.2018.
 */
public class Enemy {


    private Color color,borderColor;                //Which Color is our Enemy?
    private EnemyBehavior behavior;     //State-Machine
    private Statistics stats;

    private int id;
    private int size, collisionSize;

    private GameEvent onDeathEvent;
    private GameEvent onCollisionEvent;
    private GameEvent onHitEvent;

    /*e.add(getStatsFor(22));
        e.add(new PositionComponent(x, y));
        e.add(new CollisionComponent());
        e.add(new CircleCollisionComponent(150f, 150f, 13));
        e.add(new DebugCircleComponent(11));
        e.add(new VelocityComponent());
        e.add(getColorFor(20));
        e.add(new DisplacableComponent(100));
        e.add(getAIFor(22));
        e.add(new EnemyHitboxComponent(20));
        e.add(new EnemyComponent(x, y, room, 21));

        enMapper.get(e).setOnDeath(new OnDeathEvent() {
        @Override
        public boolean onDeath(Entity us, Entity them) {
            PositionComponent pos = posMapper.get(us);
            EnemyComponent en = enMapper.get(us);
            Entity one = EnemyFactory.createEnemy(0, pos.getX() + 5, pos.getY(), en.getRoom());
            Entity two = EnemyFactory.createEnemy(0, pos.getX() - 5, pos.getY(), en.getRoom());
            Event e = new Event(Consts.SPAWN_ENEMY);
            e.addData("1", one);
            e.addData("2", two);
            EventBus.getInstance().addEvent(e);
            return true;
        }
    });*/

    public Enemy(Color color, Color borderColor, int size, int displaySize, EnemyBehavior behavior, Statistics stats, GameEvent onDeathEvent, GameEvent onCollisionEvent, GameEvent onHitEvent) {

        this.color = color;
        this.borderColor = borderColor;
        this.collisionSize = size;
        this.size = displaySize;
        this.behavior = behavior;
        this.stats = stats;


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

        EnemyComponent ec = new EnemyComponent(x, y, room, id);
        ec.setOnDeath(onDeathEvent);
        e.add(ec);



        return e;
    }


}
