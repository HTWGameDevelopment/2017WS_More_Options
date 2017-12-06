package com.moreoptions.prototype.gameEngine.data.ai.movement;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.data.pathfinding.Node;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Andreas on 06.12.2017.
 */
public class BlinkerMoveState implements AIState {

    private float cooldown = 1;
    private float currentProgress = 0;

    private Entity player;

    private PositionComponent playerPos;
    private PositionComponent ownPos;

    private Random random;
    private ArrayList<Node> path;
    @Override
    public void update(Room room, Entity self, float delta) {
        player = getClosestPlayer(room.getPlayerList(), self);

        if(currentProgress > cooldown) {
            try {
                playerPos = player.getComponent(PositionComponent.class);
                ownPos = self.getComponent(PositionComponent.class);

                random = new Random();

                float x;
                float y;
                x = random.nextInt(250) + 64;
                y = random.nextInt(180) + 64;
                path = room.getNavGraph().getPath(ownPos.getX(), ownPos.getY(), x, y);

                while(path.isEmpty() || path.size() == 0) {

                    x = random.nextInt(250) + 64;
                    y = random.nextInt(180) + 64;
                    path = room.getNavGraph().getPath(ownPos.getX(), ownPos.getY(), x, y);
                }

                    ownPos.setX(random.nextInt(250) + 64);
                    ownPos.setY(random.nextInt(164) + 64);



            } catch (Exception ex) {
                ex.printStackTrace();
            }
            currentProgress = 0;
        }
        System.out.println(delta);
        currentProgress += delta;
    }

    @Override
    public void draw(ShapeRenderer renderer) {

    }

    public Entity getClosestPlayer (ArrayList<Entity> playerList, Entity self) {
        return playerList.get(0);
    }
}
