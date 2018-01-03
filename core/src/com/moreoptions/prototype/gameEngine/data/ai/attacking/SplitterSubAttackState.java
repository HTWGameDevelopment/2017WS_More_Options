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
import com.moreoptions.prototype.gameEngine.util.ProjectileFactory;

import java.util.ArrayList;
import java.util.Random;

public class SplitterSubAttackState implements AIState {

    private ComponentMapper<AIComponent> aiMapper = ComponentMapper.getFor(AIComponent.class);

    private static final float COOLDOWN = 1.0f;
    private float currentProgress = 0f;


    @Override
    public void update(Room room, Entity self, float deltaTime) {
        Entity player = getClosestPlayer(room.getPlayerList(), self);
        PositionComponent playerPos = player.getComponent(PositionComponent.class);
        PositionComponent ownPos = self.getComponent(PositionComponent.class);

        float distance = ownPos.getPosition().dst(playerPos.getPosition());


        try {

            if (distance > 100){
                aiMapper.get(self).setState("MOVE");
            }

            if( currentProgress >= COOLDOWN) {

                Random random = new Random();
                Vector2 dir = new Vector2(playerPos.getX() - ownPos.getX(), playerPos.getY() - ownPos.getY());
                dir.nor();

                for(int i = 0; i < 2; i++) {
                    Entity projectile1 = ProjectileFactory.enemyProjectile(self, dir.cpy().rotate(random.nextInt(50 + 50 + 1) - 50));

                    GameWorld.getInstance().addEntity(projectile1);
                }



                currentProgress = 0;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        currentProgress += deltaTime;
    }

    @Override
    public void draw(ShapeRenderer renderer) {

    }

    public Entity getClosestPlayer (ArrayList<Entity> playerList, Entity self) {
        return playerList.get(0);
    }
}


