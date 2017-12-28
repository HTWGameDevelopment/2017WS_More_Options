package com.moreoptions.prototype.gameEngine.util.navgraph;

import java.util.ArrayList;

/**
 * Created by denwe on 24.12.2017.
 */
public class Node {

    private float x, y;

    private ArrayList<Edge> edges = new ArrayList<Edge>();


    public Node(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Node> getNeighbors() {
        ArrayList<Node> n = new ArrayList<Node>();
        for(Edge e : edges) {
            n.add(e.endNode);
        }
        return n;
    }

    public void removeNeighbor(Node n) {
        ArrayList<Edge> removeEdges = new ArrayList<Edge>();

        for(Edge edge : edges) {
            if(edge.endNode == n) {
                removeEdges.add(edge);
            }
        }

        edges.removeAll(removeEdges);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
