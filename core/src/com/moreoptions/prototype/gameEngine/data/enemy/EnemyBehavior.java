package com.moreoptions.prototype.gameEngine.data.enemy;

import com.badlogic.ashley.core.Component;
import com.moreoptions.prototype.gameEngine.components.AIComponent;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;

import java.util.HashMap;

/**
 * Enemybehavior is defined as a state machine. A set of states is supplied, as well as a starting state.
 */
public class EnemyBehavior {

    private AIState startState;
    private HashMap<String , AIState> stateMap;

    private Statistics stats;


    public EnemyBehavior(AIState startState, HashMap<String, AIState> stateMap, Statistics stats) {
        this.startState = startState;
        this.stateMap = stateMap;
        this.stats = stats;
    }

    public AIComponent getAIComponent() {

        AIComponent ai = new AIComponent(startState, stateMap);
        return ai;
    }

    public Statistics getStats() {
        return stats;
    }

    public static class EnemyBehaviorBuilder {

        private AIState startState = null;
        private HashMap<String , AIState> stateMap = new HashMap<String, AIState>();
        private Statistics stats;

        public EnemyBehaviorBuilder setStats(Statistics stats) {
            this.stats = stats;
            return this;
        }

        public EnemyBehaviorBuilder addState(String id, AIState state) {
            if (!stateMap.containsKey(id)) {
                stateMap.put(id, state);
                return this;
            } else throw new EnemyBehaviorBuilderException(Consts.Error.BEHAVIOR_ALREADY_DEFINED +":" + id);
        }

        public EnemyBehaviorBuilder setStartState(String id) {
            if(stateMap.containsKey(id)) {
                startState = stateMap.get(id);
                return this;
            }
            else throw new EnemyBehaviorBuilderException(Consts.Error.STARTSTATE_NOT_FOUND + ":"+ id);
        }

        public EnemyBehavior build() {

            return new EnemyBehavior(startState, stateMap, stats);

        }

        private class EnemyBehaviorBuilderException extends RuntimeException {
            public EnemyBehaviorBuilderException(String s) {
                super(s);
            }
        }
    }

}
