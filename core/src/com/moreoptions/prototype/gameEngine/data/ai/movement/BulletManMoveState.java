package com.moreoptions.prototype.gameEngine.data.ai.movement;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.data.pathfinding.Node;

/**
 * Created by Andreas on 03.01.2018.
 */
public class BulletManMoveState implements AIState {

    private static final float SHOOT_COOLDOWN = 2.0f;

    private Vector2 targetPos;
    private Vector2 directionVector = new Vector2(0, 0);

    private boolean shot = true;

    private ComponentMapper<AIComponent> aiMapper = ComponentMapper.getFor(AIComponent.class);

    private float shotProgress = 0;
    private ComponentMapper<StatsComponent> statsMapper = ComponentMapper.getFor(StatsComponent.class);

    @Override
    public void update(Room room, Entity self, float deltaTime) {
        try {
            PositionComponent ownPos = self.getComponent(PositionComponent.class);
            VelocityComponent ownVel = self.getComponent(VelocityComponent.class);
            Statistics stats        = self.getComponent(StatsComponent.class).getStats();

            move(room, self, ownPos, ownVel);
            // shoot
            if (stats.getCurrentShotCooldown() >= stats.getFireRate()) {
                if (!shot) {
                    aiMapper.get(self).setState("ATTACK");
                    stats.setCurrentShotCooldown(0);
                    shot = true;
                } else {
                    // move around
                    move(room, self, ownPos, ownVel);
                    shot = false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        shotProgress += deltaTime;
    }

    @Override
    public void draw(ShapeRenderer renderer) {

    }

    private void move(Room room, Entity self, PositionComponent ownPos, VelocityComponent ownVel) {
        if(targetPos == null) {
            targetPos = room.getNavGraph().getRandomPosition(self);
        } else {
            if (hasReached(self)) {

                targetPos = room.getNavGraph().getRandomPosition(self);

                directionVector = calculateDirection(targetPos, ownPos);

                chase(ownVel, directionVector,self);
            } else {
                directionVector = calculateDirection(targetPos, ownPos);
                chase(ownVel, directionVector, self);
            }
        }
    }

    private boolean hasReached(Entity self) {
        CircleCollisionComponent hitbox = self.getComponent(CircleCollisionComponent.class);
        return hitbox.getHitbox().contains(targetPos.x, targetPos.y);
    }

    private Vector2 calculateDirection(Vector2 targetPos, PositionComponent ownPos) {
        Vector2 hf = targetPos.cpy().sub(ownPos.getX(), ownPos.getY());
        hf.nor();
        return hf;
    }

    private void chase(VelocityComponent ownVel, Vector2 dir,Entity self) {
        StatsComponent p = statsMapper.get(self);
        ownVel.setVelX(dir.x * p.getStats().getSpeed());
        ownVel.setVelY(dir.y * p.getStats().getSpeed());
    }
}
