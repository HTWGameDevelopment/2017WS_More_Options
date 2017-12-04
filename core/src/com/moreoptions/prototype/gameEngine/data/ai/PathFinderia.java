package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.components.AIComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import javafx.geometry.Pos;

import java.util.ArrayList;

public class PathFinderia {
    public ArrayList<Node> getPath(Entity self, Entity target){
        ArrayList<Node> nodesVisited;
        ArrayList<Node> openNodes = new ArrayList<Node>();
        StandardCSpace stdCspace = self.getComponent(AIComponent.class).getCspace();
        ArrayList<Node> allNodes = stdCspace.getNodes();

        Node playerNode = new Node(target.getComponent(PositionComponent.class).getX(), target.getComponent(PositionComponent.class).getY());
        Node selfNode = new Node(self.getComponent(PositionComponent.class).getX(), self.getComponent(PositionComponent.class).getY());




        Node currentNode = selfNode;

        ArrayList<Node> path = new ArrayList<Node>();
        path.add(selfNode);

        while(currentNode != playerNode) {

        }
        return null;
    }

    public Node getClosestNode(Node currentNode){
        return null;
    }

    public Node getClosestNode(ArrayList<Node> openNodes, Entity self){
        Node consideredNode = null;
        float distance = 0;
        for(Node n : openNodes){
            if (consideredNode == null){
                distance = getDistance(self, n);
                consideredNode = n;
            } else {

                if(getDistance(self, n) < distance) {
                    consideredNode = n;
                    distance = getDistance(self, n);
                }
            }
        }
        return null;
    }

    private float getDistance(Entity self, Node n) {
        PositionComponent p = self.getComponent(PositionComponent.class);

        return (float) Math.hypot(p.getX() - n.getX(), p.getY() - n.getY());

    }
}

