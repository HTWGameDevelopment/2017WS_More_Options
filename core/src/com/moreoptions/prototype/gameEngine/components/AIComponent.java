package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.data.ai.Node;
import com.moreoptions.prototype.gameEngine.data.ai.StandardCSpace;

import java.util.ArrayList;
import java.util.HashMap;

public class AIComponent implements Component {

    HashMap<String, AIState> stateMap= new HashMap<String, AIState>();
    StandardCSpace cSpace;
    AIState currentState;
    public StandardCSpace getCspace() {
        return cSpace;
    }
}
