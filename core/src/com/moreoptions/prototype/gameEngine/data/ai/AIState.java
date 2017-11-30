package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;

public interface AIState {
    void update(ArrayList<Entity> playerList, StandardCSpace cSpace, Entity self);

}
