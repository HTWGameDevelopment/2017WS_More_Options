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
import java.util.Random;

/**
 * Created by Andreas on 02.01.2018.
 */
public class BulletManAttackState implements AIState{
    private ComponentMapper<AIComponent> aiMapper = ComponentMapper.getFor(AIComponent.class);
    private Random random = new Random();
    private Entity player;
    private int attackType;

    @Override
    public void update(Room room, Entity self, float deltaTime) {
        try {
            attackType = getNextAttack();

            switch (attackType) {
                case 0:
                    shootInAllDirections(self);
                    break;

                case 1:
                    barfAttack(room, self);
                    break;

                default:
                    shootInAllDirections(self);
            }
            aiMapper.get(self).setState(Consts.Ai.MOVE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void barfAttack(Room room, Entity self) {
        player = getClosestPlayer(room.getPlayerList(), self);

        PositionComponent ppc = player.getComponent(PositionComponent.class);
        PositionComponent opc = self.getComponent(PositionComponent.class);

        for (int i = 0; i <= 15; i++) {
            Vector2 shot = new Vector2(ppc.getX() - opc.getX(), ppc.getY() - opc.getY());
            shot.nor();
            shot.rotate(random.nextInt(60) - 60);
            EventFactory.createShot(self, shot);
        }
    }

    private void shootInAllDirections(Entity self) {
        for (int i = 0; i <= 8; i++) {
            Vector2 dir = new Vector2(1, 0);
            dir.rotate(45 * i);
            EventFactory.createShot(self, dir);

        }
    }

    @Override
    public void draw(ShapeRenderer renderer) {
    }

    private int getNextAttack() {
        return random.nextInt(2);
    }

    public Entity getClosestPlayer (ArrayList<Entity> playerList, Entity self){
        return playerList.get(0);
    }
}
