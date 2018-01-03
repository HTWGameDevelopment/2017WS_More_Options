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
            aiMapper.get(self).setState("MOVE");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void barfAttack(Room room, Entity self) {
        player = getClosestPlayer(room.getPlayerList(), self);

        PositionComponent ppc = player.getComponent(PositionComponent.class);
        PositionComponent opc = self.getComponent(PositionComponent.class);

        try {
            ArrayList<Vector2> projectiles = new ArrayList<Vector2>();
            ArrayList<Vector2> directions = new ArrayList<Vector2>();
            for (int i = 0; i <= 15; i++) {
                Vector2 shot = new Vector2(ppc.getX() - opc.getX(), ppc.getY() - opc.getY());
                shot.nor();
                shot.rotate(random.nextInt(60) - 60 );


            }

            for(Vector2 projectile : projectiles) {
                Vector2 dir = projectile.nor();
                dir.rotate(random.nextInt(30));
                directions.add(dir);
            }

            for (Vector2 dir : directions) {
                Entity shot = ProjectileFactory.enemyProjectile(self, dir);
                GameWorld.getInstance().addEntity(shot);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void shootInAllDirections(Entity self) {
        ArrayList<Vector2> projectiles = new ArrayList<Vector2>();
        for (int i = 0; i <= 8; i++) {
            Vector2 dir = new Vector2(1, 0);
            projectiles.add(dir);
        }

        int count = 1;
        for (Vector2 dir : projectiles) {
            dir.rotate(45*count);
            Entity projectile = ProjectileFactory.enemyProjectile(self, dir);
            GameWorld.getInstance().addEntity(projectile);
            count++;
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
