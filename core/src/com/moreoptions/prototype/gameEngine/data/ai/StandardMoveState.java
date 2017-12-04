package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.VelocityComponent;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.exceptions.NoValidComponentException;

import java.util.ArrayList;

public class StandardMoveState implements AIState {

    Node target = new Node(0,0);

    @Override
    public void update(Room r, Entity self) {

        Entity player = getClosestPlayer(r.getPlayerList(),self);

        try {
            ArrayList<Node> path = r.getNavGraph().getPath(self,player);
            if(!path.isEmpty()) {


                VelocityComponent velC = self.getComponent(VelocityComponent.class);
                PositionComponent posC = self.getComponent(PositionComponent.class);

                target = path.get(0);

                while(target.getCameFrom().getX() != posC.getX() && target.getY() != posC.getY()) {
                    target = target.getCameFrom();
                }


                float tx = target.getX() - posC.getX();
                float ty = target.getY() - posC.getY();
                float dist = (float) Math.sqrt(tx * tx + ty * ty);

                velC.setVelX((tx / dist) * 100);
                velC.setVelY((ty / dist) * 100);

                System.out.println(posC.getX());

            }
        } catch (NoValidComponentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void draw(ShapeRenderer renderer) {
        renderer.circle(target.getX(),target.getY(),5);
    }

    public Entity getClosestPlayer (ArrayList<Entity> playerList, Entity self){

        return playerList.get(0);
    }
}
