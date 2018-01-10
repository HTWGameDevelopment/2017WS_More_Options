package com.moreoptions.prototype.gameEngine.data.ai.movement;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moreoptions.prototype.gameEngine.components.AIComponent;
import com.moreoptions.prototype.gameEngine.components.EnemyComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.VelocityComponent;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.data.exceptions.NoValidComponentException;
import com.moreoptions.prototype.gameEngine.data.pathfinding.Node;
import com.moreoptions.prototype.gameEngine.data.pathfinding.Path;

import java.util.ArrayList;

public class SplitterMoveState implements AIState {


    Node target = new Node(0,0);
    private Entity player;

    private VelocityComponent velC;
    private PositionComponent posC;

    private float tx;
    private float ty;
    private float dist;
    private Path path;

    private ComponentMapper<AIComponent> aiMapper = ComponentMapper.getFor(AIComponent.class);
    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);

    @Override
    public void update(Room r, Entity self,float delta) {

        PositionComponent pos = posMapper.get(self);
        player = getClosestPlayer(r.getPlayerList(),self);

        try {
            PositionComponent playerPos = posMapper.get(player);

            float distance = pos.getPosition().dst(playerPos.getPosition());

            path = r.getNavGraph().getPath(self,player);
            if(path.isValid()) {

                velC = self.getComponent(VelocityComponent.class);
                posC = self.getComponent(PositionComponent.class);

                target = path.getNext();

                tx = target.getX() - posC.getX();
                ty = target.getY() - posC.getY();
                dist = (float) Math.sqrt(tx * tx + ty * ty);

                velC.setVelX((tx / dist) * 100);
                velC.setVelY((ty / dist) * 100);

            } else {
                velC.setVelX(0);
                velC.setVelY(0);
            }
            if (distance <= 80){
                velC.setVelX(0);
                velC.setVelY(0);
                aiMapper.get(self).setState(Consts.Ai.ATTACK);
            }
        } catch (NoValidComponentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        if(path != null) {
            ArrayList<Node> nodes = path.getNodes();


            if(nodes != null) {
                Node t = nodes.get(0);
                renderer.line(posC.getX(), posC.getY(), t.getX(), t.getY());

                if (!nodes.isEmpty()) {
                    for (int i = 0; i < nodes.size() - 1; i++) {
                        Node start = nodes.get(i);
                        Node end = nodes.get(i + 1);

                        renderer.line(start.getX(), start.getY(), end.getX(), end.getY());
                    }
                }
            }
        }
        renderer.circle(target.getX(),target.getY(),5);
    }

    public Entity getClosestPlayer (ArrayList<Entity> playerList, Entity self){

        return playerList.get(0);
    }
}
