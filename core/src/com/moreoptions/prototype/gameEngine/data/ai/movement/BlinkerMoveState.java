package com.moreoptions.prototype.gameEngine.data.ai.movement;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.data.pathfinding.Node;
import com.moreoptions.prototype.gameEngine.data.pathfinding.Path;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Andreas on 06.12.2017.
 */
public class BlinkerMoveState implements AIState {

    private static final float COOLDOWN = 1;
    private float currentProgress = 0;

    private Entity player;

    private PositionComponent playerPos;
    private PositionComponent ownPos;

    private Random random = new Random();
    private Path path;

    private Vector2 ownVec = new Vector2();
    private Vector2 playerVec = new Vector2();
    private Vector2 distanceVec = new Vector2();


    private boolean attacking = false;

    @Override
    public void update(Room room, Entity self, float delta) {
        player = getClosestPlayer(room.getPlayerList(), self);

        try {

            playerPos = player.getComponent(PositionComponent.class);
            ownPos = self.getComponent(PositionComponent.class);

            distanceVec = ownVec.sub(playerVec);


            if (distanceVec.len2() <= 1) {
                attacking = true;
                System.out.println("BLINKER IS ATTACKING");
            } else {
                if (currentProgress > COOLDOWN) {
                    System.out.println("BLINKER IS TELEPORTING");
                    teleport(room, self);
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        currentProgress += delta;
    }

    private void teleport(Room room, Entity self) {
        float x;
        float y;
        x = random.nextInt(250) + 64;
        y = random.nextInt(180) + 64;
        path = room.getNavGraph().getPath(ownPos.getX(), ownPos.getY(), x, y);

        while(path.isValid()) {

            x = random.nextInt(250) + 64;
            y = random.nextInt(180) + 64;
            path = room.getNavGraph().getPath(ownPos.getX(), ownPos.getY(), x, y);
        }

        ownPos.setX(random.nextInt(250) + 64);
        ownPos.setY(random.nextInt(164) + 64);
    }

    @Override
    public void draw(ShapeRenderer renderer) {

    }

    public Entity getClosestPlayer (ArrayList<Entity> playerList, Entity self) {
        return playerList.get(0);
    }
}
