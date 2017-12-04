package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;

import java.util.ArrayList;

public class StandardMoveState implements AIState {
    @Override
    public void update(ArrayList<Entity> playerList, StandardCSpace cSpace, Entity self) {

    }

    public Entity getClosestPlayer (ArrayList<Entity> playerList, Entity self){
        PositionComponent epc = self.getComponent(PositionComponent.class);
        for (Entity p : playerList){
            PositionComponent ppc = p.getComponent(PositionComponent.class);


            return null;
        }
        return null;
    }
}
