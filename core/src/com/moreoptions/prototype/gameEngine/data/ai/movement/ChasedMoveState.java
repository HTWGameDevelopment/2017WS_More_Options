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
    private Vector2 playerVec = new Vector2();
    private Vector2 ownVec = new Vector2();

    @Override
    public void update(Room room, Entity self, float delta) {
        Entity player = getClosestPlayer(room.getPlayerList(), self);

        try {

            PositionComponent playerPos = player.getComponent(PositionComponent.class);
            VelocityComponent playerVel = player.getComponent(VelocityComponent.class);

            PositionComponent ownPos = self.getComponent(PositionComponent.class);
            VelocityComponent ownVel = self.getComponent(VelocityComponent.class);

            playerVec.set(playerPos.getX(), playerPos.getY());
            ownVec.set(ownPos.getX(), ownPos.getY());

             Vector2 dirVector = ownVec.sub(playerVec);
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
