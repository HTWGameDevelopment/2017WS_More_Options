package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.components.AIComponent;

import java.util.ArrayList;

public class PathFinderia {
    public ArrayList<Node> getPath(Entity self, Entity target){
        ArrayList<Node> nodesVisited;
        ArrayList<Node> openNodes = new ArrayList<Node>();
        StandardCSpace stdCspace = self.getComponent(AIComponent.class).getCspace();
        ArrayList<Node> allNodes = stdCspace.getNodes();

        for (Node n : allNodes){
            if (!n.isBlocked()){
                openNodes.add(n);
            }
        }
    }

    public Node getClosestNode(Node currentNode){

    }

    public Node getClosestNode(ArrayList<Node> openNodes, Entity self){
        Node consideredNode = null;
        float distance = 0;
        for(Node n : openNodes){
            if (consideredNode == null){
                getDistance();
                consideredNode = n;
            }
            if (consideredNode)
        }
    }
}
