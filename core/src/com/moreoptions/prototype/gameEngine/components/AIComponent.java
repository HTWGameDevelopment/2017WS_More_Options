package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.data.ai.Node;
import com.moreoptions.prototype.gameEngine.data.ai.StandardCSpace;
import com.moreoptions.prototype.gameEngine.data.ai.StandardMoveState;

import java.util.ArrayList;
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
