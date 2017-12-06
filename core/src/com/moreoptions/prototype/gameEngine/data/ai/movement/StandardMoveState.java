package com.moreoptions.prototype.gameEngine.data.ai.movement;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.VelocityComponent;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.data.pathfinding.Node;
import com.moreoptions.prototype.gameEngine.data.exceptions.NoValidComponentException;
import javafx.geometry.Pos;

import java.util.ArrayList;

public class StandardMoveState implements AIState {

    Node target = new Node(0,0);
    private Entity player;

    private VelocityComponent velC;
    private PositionComponent posC;

    private float tx;
    private float ty;
    private float dist;


    @Override
    public void update(Room r, Entity self,float delta) {

        player = getClosestPlayer(r.getPlayerList(),self);

        try {
            ArrayList<Node> path = r.getNavGraph().getPath(self,player);
            if(!path.isEmpty()) {


                velC = self.getComponent(VelocityComponent.class);
                posC = self.getComponent(PositionComponent.class);

                target = path.get(0);

                while(target.getCameFrom().getX() != posC.getX() && target.getY() != posC.getY()) {
                    target = target.getCameFrom();
                }


                tx = target.getX() - posC.getX();
                ty = target.getY() - posC.getY();
                dist = (float) Math.sqrt(tx * tx + ty * ty);

                velC.setVelX((tx / dist) * 100);
                velC.setVelY((ty / dist) * 100);


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
