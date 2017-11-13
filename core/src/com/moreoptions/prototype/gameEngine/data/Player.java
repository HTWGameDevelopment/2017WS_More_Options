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

    private PlayerStatistics stats;

    public Player() {
        inputState = new InputState();
    }

    public InputState getInputState() {
        return inputState;
    }


}
