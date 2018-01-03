package com.moreoptions.prototype.gameEngine.data.ai.movement;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.AIComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;

import java.util.ArrayList;


/**
 * Created by Andreas on 06.12.2017.
 */
public class BlinkerMoveState implements AIState {

    private static final float COOLDOWN = 1.5f;
    private float currentProgress = 0;

    private boolean shot = false;

    private ComponentMapper<AIComponent> aiMapper = ComponentMapper.getFor(AIComponent.class);

    @Override
    public void update(Room room, Entity self, float delta) {
        Entity player = getClosestPlayer(room.getPlayerList(), self);

        try {

            PositionComponent playerPos = player.getComponent(PositionComponent.class);
            PositionComponent ownPos = self.getComponent(PositionComponent.class);

            float distance = ownPos.getPosition().dst(playerPos.getPosition());

            if (distance > 70 && currentProgress >= COOLDOWN) {
                teleport(room, self, ownPos);
                currentProgress = 0;
                shot = false;

            } else if (distance <= 70) {
                if (!shot) {
                    aiMapper.get(self).setState("ATTACK");
                    shot = true;
                } else if (currentProgress > COOLDOWN){
                    teleport(room, self, ownPos);
                    currentProgress = 0;
                    shot = false;
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        currentProgress += delta;
    }

    private void teleport(Room room, Entity self, PositionComponent ownPos) {

        Vector2 randPos = room.getNavGraph().getRandomPosition(self);

        ownPos.setX(randPos.x);
        ownPos.setY(randPos.y);
    }

    @Override
    public void draw(ShapeRenderer renderer) {

    }

    public Entity getClosestPlayer (ArrayList<Entity> playerList, Entity self) {
        return playerList.get(0);
    }
}
