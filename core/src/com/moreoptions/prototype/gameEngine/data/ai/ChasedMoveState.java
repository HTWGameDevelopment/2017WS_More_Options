package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.VelocityComponent;
import com.moreoptions.prototype.gameEngine.data.Room;

import java.util.ArrayList;

/**
 * Created by Andreas on 06.12.2017.
 */
public class ChasedMoveState implements AIState {

    Vector2 ownPosition = new Vector2();
    Vector2 velocity = new Vector2();

    @Override
    public void update(Room room, Entity self) {
        Entity player = getClosestPlayer(room.getPlayerList(),self);

        try {

            PositionComponent playerPos = player.getComponent(PositionComponent.class);
            VelocityComponent playerVel = player.getComponent(VelocityComponent.class);

            PositionComponent ownPos = self.getComponent(PositionComponent.class);
            VelocityComponent ownVel = self.getComponent(VelocityComponent.class);

            Vector2 playerVec = new Vector2(playerPos.getX(), playerPos.getY());
            Vector2 ownVec = new Vector2(ownPos.getX(), ownPos.getY());

            Vector2 dirVector = ownVec.sub(playerVec);

            dirVector.nor();

            ownVel.setVelX(dirVector.x*50);
            ownVel.setVelY(dirVector.y*50);

            velocity.x = ownVel.getVelX();
            velocity.y = ownVel.getVelY();

            ownPosition.x = ownPos.getX();
            ownPosition.y = ownPos.getY();



        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        renderer.line(velocity.x,velocity.y,ownPosition.x,ownPosition.y);
    }

    public Entity getClosestPlayer (ArrayList<Entity> playerList, Entity self){

        return playerList.get(0);
    }
}
