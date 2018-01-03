package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.data.ai.attacking.*;
import com.moreoptions.prototype.gameEngine.data.ai.movement.BlinkerMoveState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.ChasedMoveState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.SplitterMoveState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.StandardMoveState;
import com.moreoptions.prototype.gameEngine.data.callback.OnDeathEvent;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventBus;

import java.util.HashMap;

/**
 * Created by Andreas on 07.12.2017.
 */
public class EnemyFactory {

    private static ComponentMapper<EnemyComponent> enMapper = ComponentMapper.getFor(EnemyComponent.class);
    private static ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);


    public static Entity createEnemy(int enemyId, float x, float y, Room room) {

        switch (enemyId) {
            case 20:
                return createSplitter(x,y,room);

            case 21:
                return createSplitterSub(x,y,room);

            case 22:
                return createSplitterSubSub(x,y,room);

            case 23:
                return createSentry(x,y,room);

            default:
                Entity e = new Entity();
                e.add(getStatsFor(enemyId));
                e.add(new PositionComponent(x, y));
                e.add(new CollisionComponent());
                e.add(new CircleCollisionComponent(150f, 150f, 4));
                e.add(new DebugCircleComponent(10));
                e.add(new VelocityComponent(0f, 0f));
                e.add(getColorFor(enemyId));            // Chaser - Forest, Chased - Gold, Blinker - Cyan
                e.add(new DisplacableComponent(10));
                e.add(getAIFor(enemyId));
                e.add(new EnemyHitboxComponent(10));
                e.add(new EnemyComponent(x, y, room,enemyId));

                return e;
        }
    }

    private static Entity createSentry(float x, float y, Room room) {
        Entity e = new Entity();
        e.add(getStatsFor(23));
        e.add(new PositionComponent(x, y));
        e.add(new CollisionComponent());
        e.add(new CircleCollisionComponent(150f, 150f, 13));
        e.add(new DebugSquareComponent(12));
        e.add(new VelocityComponent(0f, 0f));
        e.add(getColorFor(23));
        e.add(new DisplacableComponent(100));
        e.add(getAIFor(23));
        e.add(new EnemyHitboxComponent(20));
        e.add(new EnemyComponent(x, y, room,20));

        return e;

    }


    private static Entity createSplitter(float x, float y, Room room) {

        Entity e = new Entity();
        e.add(getStatsFor(20));
        e.add(new PositionComponent(x, y));
        e.add(new CollisionComponent());
        e.add(new CircleCollisionComponent(150f, 150f, 32));
        e.add(new DebugCircleComponent(30));
        e.add(new VelocityComponent(0f, 0f));
        e.add(getColorFor(20));
        e.add(new DisplacableComponent(100));
        e.add(getAIFor(20));
        e.add(new EnemyHitboxComponent(20));
        e.add(new EnemyComponent(x, y, room,20));

        enMapper.get(e).setOnDeath(new OnDeathEvent() {
            @Override
            public boolean onDeath(Entity us, Entity them) {
                PositionComponent pos = posMapper.get(us);
                EnemyComponent en = enMapper.get(us);
                Entity one = EnemyFactory.createEnemy(21, pos.getX() + 5, pos.getY(), en.getRoom());
                Entity two = EnemyFactory.createEnemy(21, pos.getX() - 5, pos.getY(), en.getRoom());
                Event e = new Event(Consts.SPAWN_ENEMY);
                e.addData("1", one);
                e.addData("2", two);
                EventBus.getInstance().addEvent(e);
                return true;
            }
        });

        return e;
    }
    private static Entity createSplitterSub(float x, float y, Room room) {

        Entity e = new Entity();
        e.add(getStatsFor(21));
        e.add(new PositionComponent(x, y));
        e.add(new CollisionComponent());
        e.add(new CircleCollisionComponent(150f, 150f, 22));
        e.add(new DebugCircleComponent(20));
        e.add(new VelocityComponent(0f, 0f));
        e.add(getColorFor(20));
        e.add(new DisplacableComponent(100));
        e.add(getAIFor(21));
        e.add(new EnemyHitboxComponent(20));
        e.add(new EnemyComponent(x, y, room,21));

        enMapper.get(e).setOnDeath(new OnDeathEvent() {
            @Override
            public boolean onDeath(Entity us, Entity them) {
                PositionComponent pos = posMapper.get(us);
                EnemyComponent en = enMapper.get(us);
                Entity one = EnemyFactory.createEnemy(22, pos.getX() + 5, pos.getY(), en.getRoom());
                Entity two = EnemyFactory.createEnemy(22, pos.getX() - 5, pos.getY(), en.getRoom());
                Event e = new Event(Consts.SPAWN_ENEMY);
                e.addData("1", one);
                e.addData("2", two);
                EventBus.getInstance().addEvent(e);
                return true;
            }
        });

        return e;
    }

    private static Entity createSplitterSubSub(float x, float y, Room room) {

        Entity e = new Entity();
        e.add(getStatsFor(22));
        e.add(new PositionComponent(x, y));
        e.add(new CollisionComponent());
        e.add(new CircleCollisionComponent(150f, 150f, 13));
        e.add(new DebugCircleComponent(11));
        e.add(new VelocityComponent(0f, 0f));
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
        });
        return e;
    }

    private static Component getStatsFor(int enemyId) {
        switch(enemyId){
            case 20:
                StatsComponent statsSplitter = new StatsComponent();
                statsSplitter.getStats().setMaxHealth(15);
                statsSplitter.getStats().setCurrentHealth(15);
                statsSplitter.getStats().setDamage(2);
                statsSplitter.getStats().setSpeed(30);
                statsSplitter.getStats().setProjectileSpeed(140);
                statsSplitter.getStats().setRange(300);
                return statsSplitter;

            case 21:
                StatsComponent statsSplitterSub = new StatsComponent();
                statsSplitterSub.getStats().setMaxHealth(8);
                statsSplitterSub.getStats().setCurrentHealth(8);
                statsSplitterSub.getStats().setDamage(2);
                statsSplitterSub.getStats().setSpeed(40);
                statsSplitterSub.getStats().setProjectileSpeed(150);
                statsSplitterSub.getStats().setRange(300);
                return statsSplitterSub;

            case 22:
                StatsComponent statsSplitterSubSub = new StatsComponent();
                statsSplitterSubSub.getStats().setMaxHealth(4);
                statsSplitterSubSub.getStats().setCurrentHealth(4);
                statsSplitterSubSub.getStats().setDamage(2);
                statsSplitterSubSub.getStats().setSpeed(50);
                statsSplitterSubSub.getStats().setProjectileSpeed(160);
                statsSplitterSubSub.getStats().setRange(300);
                return statsSplitterSubSub;

            case 23:
                StatsComponent statsSentry = new StatsComponent();
                statsSentry.getStats().setMaxHealth(20);
                statsSentry.getStats().setCurrentHealth(20);
                statsSentry.getStats().setDamage(1);
                statsSentry.getStats().setProjectileSpeed(7);
                statsSentry.getStats().setRange(500);
                return statsSentry;


            default:
                return new StatsComponent();
        }

    }

    /**
     * switch to get the ColorComponent of an enemy
     * @param enemyId ID of the enemy
     * @return the DebugColorComponent of the selected enemy
     */
    private static Component getColorFor(int enemyId) {
        DebugColorComponent colorComponent;

        switch(enemyId) {
            case 0:
                // Chaser
                colorComponent = new DebugColorComponent(Color.FOREST);
                break;

            case 1:
                // Chased
                colorComponent = new DebugColorComponent(Color.GOLD);
                break;

            case 2:
                // Blinker
                colorComponent = new DebugColorComponent(Color.CYAN);
                break;

            case 20:
                // Splitter Boss
                colorComponent = new DebugColorComponent(Color.RED);
                break;

            case 23:
                // Sentrys
                colorComponent = new DebugColorComponent(Color.WHITE);
                break;

            default:
                colorComponent = new DebugColorComponent(Color.BLACK);
        }

        return colorComponent;
    }

    /**
     * switch to get the AiComponent of an enemy
     * @param enemyId ID of the enemy
     * @return the AiComponent of the selected enemy
     */
    private static Component getAIFor(int enemyId) {
        AIComponent aiComponent;
        AIState state;
        HashMap<String, AIState> stateMap= new HashMap<String, AIState>();

        switch(enemyId) {
            case 0:
                // Chaser
                // TODO: mehrere states hinzufügen
                // TODO: enums für statebeschreibungen?
                state = new StandardMoveState();
                stateMap.put("MOVE", state);
                aiComponent = new AIComponent(state, stateMap);
                break;

            case 1:
                // Chased
                state = new ChasedMoveState();
                stateMap.put("MOVE", state);
                aiComponent = new AIComponent(state, stateMap);
                stateMap.put("ATTACK", new StandardAttackState());
                break;

            case 2:

                // Blinker
                state = new BlinkerMoveState();
                stateMap.put("MOVE", state);
                aiComponent = new AIComponent(state, stateMap);
                stateMap.put("ATTACK", new StandardAttackState());
                break;

            case 20:

                //Splitter Boss
                System.out.println("BOSSTIME");
                state = new SplitterMoveState();
                stateMap.put("MOVE", state);
                stateMap.put("ATTACK", new SplitterAttackState());
                aiComponent = new AIComponent(state,stateMap);
                break;

            case 21:

                //Splitter Sub Boss
                System.out.println("BOSSTIME_ROUND2");
                state = new SplitterMoveState();
                stateMap.put("MOVE", state);
                stateMap.put("ATTACK", new SplitterSubAttackState());
                aiComponent = new AIComponent(state,stateMap);
                break;

            case 22:

                //Splitter Sub Sub Boss
                System.out.println("BOSSTIME_ROUND2");
                state = new SplitterMoveState();
                stateMap.put("MOVE", state);
                stateMap.put("ATTACK", new SplitterSubSubAttackState());
                aiComponent = new AIComponent(state,stateMap);
                break;

            case 23:

                //Sentry
                state = new SentryAttackState();
                stateMap.put("ATTACK", new SentryAttackState());
                aiComponent = new AIComponent(state,stateMap);
                break;

            default:
                // default --> Chaser
                state = new StandardMoveState();
                stateMap.put("MOVE", state);
                aiComponent = new AIComponent(state, stateMap);
        }

        return aiComponent;
    }
}
