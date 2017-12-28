package com.moreoptions.prototype.gameEngine.util.navgraph;

import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.components.ObstacleComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;

import java.util.ArrayList;

/**
 * Created by denwe on 24.12.2017.
 */
public class Edge {

    Node startNode;
    Node endNode;

    ArrayList<Entity> blockingEntities = new ArrayList();

    public Edge(Node start, Node end) {
        this.startNode = start;
        this.endNode = end;
    }


    public void addBlocker(Entity e) {
        blockingEntities.add(e);
        e.getComponent(ObstacleComponent.class).addEdge(this);
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }
}
