package com.moreoptions.prototype.gameEngine.data.ai.attacking;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.AIComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.util.EventFactory;
import com.moreoptions.prototype.gameEngine.util.ProjectileFactory;

import java.util.ArrayList;

/**
 * Created by Andreas on 02.01.2018.
 */
public class StandardAttackState implements AIState {

    private ComponentMapper<AIComponent> aiMapper = ComponentMapper.getFor(AIComponent.class);

    @Override
    public void update(Room room, Entity self, float deltaTime) {
        Entity player = getClosestPlayer(room.getPlayerList(), self);

        try {
            PositionComponent playerPos = player.getComponent(PositionComponent.class);
            PositionComponent ownPos = self.getComponent(PositionComponent.class);

            Vector2 dir = new Vector2(playerPos.getX() - ownPos.getX(), playerPos.getY() - ownPos.getY());
            dir.nor();

            EventFactory.createShot(self, dir);

            aiMapper.get(self).setState(Consts.Ai.MOVE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void draw(ShapeRenderer renderer) {    }

    public Entity getClosestPlayer (ArrayList<Entity> playerList, Entity self) {
        return playerList.get(0);
    }
}
