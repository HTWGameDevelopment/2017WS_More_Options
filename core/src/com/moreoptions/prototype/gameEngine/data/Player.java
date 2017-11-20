package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.controllers.Controller;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.level.Offset;

import javax.swing.text.Position;

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



    //Create Entity in right corner with right place
    public Entity getEntity(Offset offset) {
        Entity playerEntity = new Entity();

        addVelocityComponent(playerEntity);
        addPositionComponent(playerEntity,offset);

        addCollisionComponent(playerEntity);

        return playerEntity;

    }

    private void addPositionComponent(Entity playerEntity, Offset offset) {

        PositionComponent p;

        switch (offset) {
            case TOP:

                p = new PositionComponent(7*32, 3 * 32);

                break;
            case DOWN:

                p = new PositionComponent(7*32, 11 * 32);

                break;
            case RIGHT:
                p = new PositionComponent(3*32, 6 * 32);
                break;
            case LEFT:
                p = new PositionComponent(9*32, 6 * 32);
                break;
            default:
                p = new PositionComponent(50,50);
        }

        playerEntity.add(p);
    }

    private void addCollisionComponent(Entity playerEntity) {

    }

    private void addVelocityComponent(Entity playerEntity) {

    }
}
