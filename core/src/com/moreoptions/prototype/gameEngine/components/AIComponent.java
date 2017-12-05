package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.data.ai.StandardMoveState;

import java.util.HashMap;

public class AIComponent implements Component {

    HashMap<String, AIState> stateMap= new HashMap<String, AIState>();
    AIState currentState;

    public AIComponent() {
        currentState = new StandardMoveState();
    }


    public AIState getState() {
        return currentState;
    }
}
