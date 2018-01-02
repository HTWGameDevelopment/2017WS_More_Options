package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.data.ai.attacking.SplitterAttackState;
import com.moreoptions.prototype.gameEngine.data.ai.attacking.StandardAttackState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.BlinkerMoveState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.ChasedMoveState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.SplitterMoveState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.StandardMoveState;

import java.util.HashMap;

/**
 * Created by Andreas on 07.12.2017.
 */
public class EnemyFactory {

    private static ComponentMapper<EnemyComponent> enMapper = ComponentMapper.getFor(EnemyComponent.class);

    public static Entity createEnemy(int enemyId, float x, float y, Room room) {

        switch (enemyId) {
            case 20:
                return createSplitter(x,y,room);


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
                e.add(new EnemyComponent(x, y, room));

                return e;
        }
    }

    private static Entity createSplitter(float x, float y, Room room) {

        Entity e = new Entity();
        e.add(getStatsFor(20));
        e.add(new PositionComponent(x, y));
        e.add(new CollisionComponent());
        e.add(new CircleCollisionComponent(150f, 150f, 20));
        e.add(new DebugCircleComponent(20));
        e.add(new VelocityComponent(0f, 0f));
        e.add(getColorFor(20));
        e.add(new DisplacableComponent(100));
        e.add(getAIFor(20));
        e.add(new EnemyHitboxComponent(20));
        e.add(new EnemyComponent(x, y, room));


        return e;
    }

    private static Component getStatsFor(int enemyId) {
        switch(enemyId){
            case 20:
                StatsComponent stats = new StatsComponent();
                stats.getStats().setMaxHealth(30);
                stats.getStats().setCurrentHealth(30);
                stats.getStats().setDamage(2);
                stats.getStats().setSpeed(30);
                stats.getStats().setProjectileSpeed(140);
                stats.getStats().setRange(300);
                return stats;

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

            default:
                // default --> Chaser
                state = new StandardMoveState();
                stateMap.put("MOVE", state);
                aiComponent = new AIComponent(state, stateMap);
        }

        return aiComponent;
    }
}
