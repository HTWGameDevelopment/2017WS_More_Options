package com.moreoptions.prototype.gameEngine.data.ai.attacking;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.AIComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.util.EventFactory;
import com.moreoptions.prototype.gameEngine.util.ProjectileFactory;

import java.util.ArrayList;
import java.util.Random;

public class SplitterAttackState implements AIState {

    private ComponentMapper<AIComponent> aiMapper = ComponentMapper.getFor(AIComponent.class);
    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);

    private static final float COOLDOWN = 1.5f;
    private float currentProgress = 3.5f;

    @Override
    public void update(Room room, Entity self, float deltaTime) {
        Entity player = getClosestPlayer(room.getPlayerList(), self);
        PositionComponent playerPos = posMapper.get(player);
        PositionComponent pos = posMapper.get(self);

        float distance = pos.getPosition().dst(playerPos.getPosition());

        try {
            if (distance > 100){
                aiMapper.get(self).setState("MOVE");
            }

            if( currentProgress >= COOLDOWN) {

                Random random = new Random();
                Vector2 dir = new Vector2(playerPos.getX() - pos.getX(), playerPos.getY() - pos.getY());
                dir.nor();

                for(int i = 0; i < 4; i++) {
                    Vector2 oi = dir.cpy().rotate(random.nextInt(50 + 50 + 1) - 50);
                    EventFactory.createShot(self, oi);

                }
                currentProgress = 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        currentProgress += deltaTime;
    }

    @Override
    public void draw(ShapeRenderer renderer) {    }

    public Entity getClosestPlayer (ArrayList<Entity> playerList, Entity self) {
        return playerList.get(0);
    }
}


