package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.exceptions.NoOffsetException;
import com.moreoptions.prototype.level.Offset;

/**
 * Player Data
 */
public class Player {

    private InputType inputType;
    private InputState inputState;

    private Controller controller;

    private Statistics stats;

    public Player() {
        inputState = new InputState();
        stats = new Statistics();
        debugStatsSetup();
    }

    public InputState getInputState() {
        return inputState;
    }



    //Create Entity in right corner with right place
    public Entity getEntity(Offset offset) {
        Entity playerEntity = new Entity();

        playerEntity.add(new PlayerComponent(this));

        try {
            addPositionComponent(playerEntity,offset);
            addVelocityComponent(playerEntity);
            addCollisionComponent(playerEntity);
            addStatsComponent(playerEntity);
            playerEntity.add(new DisplacableComponent(30));
        } catch (NoOffsetException e) {
            e.printStackTrace();
        }

        return playerEntity;

    }

    private void addStatsComponent(Entity playerEntity) {

        playerEntity.add(new StatsComponent(stats));

    }

    private void addPositionComponent(Entity playerEntity, Offset offset) throws NoOffsetException {

        PositionComponent p;



        switch (offset) {
            case TOP:

                p = new PositionComponent(8*32 + 16, 50);

                break;
            case DOWN:

                p = new PositionComponent(8*32 + 16, 9 * 32 +16 );

                break;
            case RIGHT:
                p = new PositionComponent(32+16, 6 * 32 - 16);
                break;
            case LEFT:
                p = new PositionComponent(15*32 + 16, 6 * 32 - 16);
                break;
            case NONE:
                p = new PositionComponent(Consts.GAME_WIDTH / 2,Consts.GAME_HEIGHT / 2);
                break;
            default:
                throw new NoOffsetException();
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

    public Statistics getStats() {
        return stats;
    }

    public void reset() {
        stats = new Statistics();
        debugStatsSetup();
        inputState.reset();
    }

    private void debugStatsSetup() {
        stats.setFireRate(0.5f);
        stats.setSpeed(150);
        stats.setRange(200);
        stats.setDamage(2f);
        stats.setImmunityTimer(2f);
    }
}
