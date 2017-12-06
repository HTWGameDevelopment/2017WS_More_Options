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

    float cooldown = 1;
    float currentProgress = 0;

    @Override
    public void update(Room room, Entity self, float delta) {
        Entity player = getClosestPlayer(room.getPlayerList(), self);

        if(currentProgress > cooldown) {
            try {
                PositionComponent playerPos = player.getComponent(PositionComponent.class);
                PositionComponent ownPos = self.getComponent(PositionComponent.class);

                Random random = new Random();

                // TODO: fix this!
                float x;
                float y;
                x = random.nextInt(250) + 64;
                y = random.nextInt(180) + 64;
                ArrayList<Node> path = room.getNavGraph().getPath(ownPos.getX(), ownPos.getY(), x, y);

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
        currentProgress += delta;
    }

    @Override
    public void draw(ShapeRenderer renderer) {

    }

    public Entity getClosestPlayer (ArrayList<Entity> playerList, Entity self) {
        return playerList.get(0);
    }
}
