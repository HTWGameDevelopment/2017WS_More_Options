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

    // TODO: make this an enum?
    // TODO mach das richtig du hund
    // TODO: oben einen punkt vors TODO setzen!
    // TODO: statt punkt doppelpunkt im obrigen TODO

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
