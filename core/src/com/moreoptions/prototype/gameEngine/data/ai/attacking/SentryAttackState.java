package com.moreoptions.prototype.gameEngine.data.ai.attacking;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.EnemyComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.util.EventFactory;
import com.moreoptions.prototype.gameEngine.util.ProjectileFactory;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;

import java.util.ArrayList;

public class SentryAttackState implements AIState {

    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);

    private static final float COOLDOWN = 2.0f;
    private float currentProgress = 1.5f;


    @Override
    public void update(Room room, Entity self, float deltaTime) {
        Entity player = getClosestPlayer(room.getPlayerList(), self);
        PositionComponent playerPos = posMapper.get(player);
        PositionComponent pos = posMapper.get(self);

        try {

            if( currentProgress >= COOLDOWN) {
                Vector2 dir = new Vector2(playerPos.getX() - pos.getX(), playerPos.getY() - pos.getY());
                dir.nor();
                EventFactory.createShot(self,dir);
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


