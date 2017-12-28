package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.moreoptions.prototype.gameEngine.util.navgraph.Edge;
import com.moreoptions.prototype.gameEngine.util.navgraph.Node;

import java.util.ArrayList;

/**
 * Created by denwe on 25.12.2017.
 */
public class ObstacleComponent implements Component {
    ArrayList<Edge> edges = new ArrayList<Edge>();
    ArrayList<Node> nodes = new ArrayList<Node>();
    public void addEdge(Edge edge) {
        edges.add(edge);

    }
    public void removeEdge(Edge edge) {
        if(edges.contains(edge)) edges.remove(edge);
    }

    public void addNodes(ArrayList<Node> nodes) {
        nodes.addAll(nodes);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }


    //Currently DATA-Less  component, indicating and Entity that has to be ran around.

}
