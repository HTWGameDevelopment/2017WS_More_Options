package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.BlinkerMoveState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.ChasedMoveState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.StandardMoveState;

import java.util.HashMap;

public class AIComponent implements Component {

    HashMap<String, AIState> stateMap= new HashMap<String, AIState>();
    AIState currentState;

    public AIComponent(AIState state, HashMap<String, AIState> stateMap) {
        this.currentState = state;
        this.stateMap = stateMap;
    }

    public AIComponent(int stateNumber) {
        switch (stateNumber) {
            case 1:
                currentState = new StandardMoveState();
                break;
            case 2:
                currentState = new ChasedMoveState();
                break;
            case 3:
                currentState = new BlinkerMoveState();
                break;
        }
    }

    public AIState getState() {
        return currentState;
    }
}
