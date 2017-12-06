package com.moreoptions.prototype.gameEngine.data.ai.movement;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.VelocityComponent;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;

import java.util.ArrayList;

/**
 * Created by Andreas on 06.12.2017.
 */
public class ChasedMoveState implements AIState {
    private PositionComponent playerPos;
    private VelocityComponent playerVel;

    private PositionComponent ownPos;
    private VelocityComponent ownVel;

    private Vector2 playerVec = new Vector2();
    private Vector2 ownVec = new Vector2();
    private Vector2 dirVector = new Vector2();

    private Entity player;

    @Override
    public void update(Room room, Entity self, float delta) {
        player = getClosestPlayer(room.getPlayerList(), self);

        try {

            playerPos = player.getComponent(PositionComponent.class);
            playerVel = player.getComponent(VelocityComponent.class);

            ownPos = self.getComponent(PositionComponent.class);
            ownVel = self.getComponent(VelocityComponent.class);

            playerVec.set(playerPos.getX(), playerPos.getY());
            ownVec.set(ownPos.getX(), ownPos.getY());

            dirVector = ownVec.sub(playerVec);
            dirVector.nor();

            ownVel.setVelX(dirVector.x * 25);
            ownVel.setVelY(dirVector.y * 25);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void draw(ShapeRenderer renderer) {
    }

    public Entity getClosestPlayer (ArrayList<Entity> playerList, Entity self) {
        return playerList.get(0);
    }
}
