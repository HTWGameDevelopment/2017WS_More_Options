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

/**
 * Created by Andreas on 02.01.2018.
 */
public class BulletManAttackState implements AIState{
    private ComponentMapper<AIComponent> aiMapper = ComponentMapper.getFor(AIComponent.class);

    @Override
    public void update(Room room, Entity self, float deltaTime) {
        Entity player = getClosestPlayer(room.getPlayerList(), self);

        try {
            // PositionComponent playerPos = player.getComponent(PositionComponent.class);
            // PositionComponent ownPos = self.getComponent(PositionComponent.class);

            Vector2 dirNorth = new Vector2(0, 1);
            Vector2 dirSouth = new Vector2(0, -1);
            Vector2 dirWest = new Vector2(-1, 0);
            Vector2 dirEast = new Vector2(1, 0);

            Entity projectileNorth = ProjectileFactory.enemyProjectile(self, dirNorth);
            Entity projectileSouth = ProjectileFactory.enemyProjectile(self, dirSouth);
            Entity projectileWest = ProjectileFactory.enemyProjectile(self, dirWest);
            Entity projectileEast = ProjectileFactory.enemyProjectile(self, dirEast);
            GameWorld.getInstance().addEntity(projectileNorth);
            GameWorld.getInstance().addEntity(projectileSouth);
            GameWorld.getInstance().addEntity(projectileWest);
            GameWorld.getInstance().addEntity(projectileEast);

            aiMapper.get(self).setState("MOVE");

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
