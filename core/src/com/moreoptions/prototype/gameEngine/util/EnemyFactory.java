package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.BlinkerMoveState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.ChasedMoveState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.StandardMoveState;

import java.util.HashMap;

/**
 * Created by Andreas on 07.12.2017.
 */
public class EnemyFactory {

    public static Entity createEnemy(int enemyId, float x, float y, Color color) {
        Entity e = new Entity();

        e.add(new PositionComponent(x, y));
        e.add(new CollisionComponent());
        e.add(new CircleCollisionComponent(150f, 150f, 4));
        e.add(new DebugCircleComponent(10));
        e.add(new VelocityComponent(0f, 0f));
        e.add(new DebugColorComponent(color));
        e.add(getAIFor(enemyId));
        e.add(new EnemyHitboxComponent(10));
        e.add(new EnemyComponent());

        return e;
    }

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
