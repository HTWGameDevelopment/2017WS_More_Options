package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.moreoptions.prototype.gameEngine.components.*;
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
        stats = new PlayerStatistics();
    }

    public InputState getInputState() {
        return inputState;
    }



    //Create Entity in right corner with right place
    public Entity getEntity(Offset offset) {
        Entity playerEntity = new Entity();

        playerEntity.add(new PlayerComponent(this));

        addPositionComponent(playerEntity,offset);
        addVelocityComponent(playerEntity);

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

                p = new PositionComponent(7*32, 9 * 32);

                break;
            case RIGHT:
                p = new PositionComponent(3*32, 6 * 32);
                break;
            case LEFT:
                p = new PositionComponent(14*32, 6 * 32);
                break;
            default:
                p = new PositionComponent(50,50);
        }

        System.out.println("TEST11" + p.getX() +""+ p.getY());

        playerEntity.add(p);
    }

    private void addCollisionComponent(Entity playerEntity) {

        PositionComponent pc =  playerEntity.getComponent(PositionComponent.class);

        playerEntity.add(new CollisionComponent());
        playerEntity.add(new CircleCollisionComponent(pc.getX(),pc.getY(),10));
        playerEntity.add(new DebugColorComponent(new Color(76f/255f, 176/255f, 186f/255f,1)));

    }

    private void addVelocityComponent(Entity playerEntity) {

        VelocityComponent v = new VelocityComponent(stats.getSpeed(),stats.getDeceleration());
        playerEntity.add(v);

    }
}
