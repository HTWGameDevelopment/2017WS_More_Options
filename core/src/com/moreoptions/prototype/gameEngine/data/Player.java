package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.controllers.Controller;

/**
 * Player Data
 */
public class Player {

    private InputType inputType;
    private InputState inputState;

    private Controller controller;

    Player() {
        inputState = new InputState();
    }

    public InputState getInputState() {
        return inputState;
    }


}
