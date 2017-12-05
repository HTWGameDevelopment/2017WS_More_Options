package com.moreoptions.prototype.gameEngine.data.ai;

import java.util.ArrayList;

/**
 * Created by Andreas on 30.11.2017.
 */

public class Node {

    private float x;
    private float y;
    private boolean blocked = false;

    private ArrayList<Node> neighbors = new ArrayList<Node>();
    private double heuristicDistance;
    private float cost;
    private double priority;
    private Node cameFrom;
    private boolean marked;

    public Node(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void addNeighbor(Node n) {
        if(!neighbors.contains(neighbors)) {
            neighbors.add(n);
        }
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public ArrayList<Node> getNeighbours() {
        return neighbors;
    }

    public void setHeuristicDistance(double heuristicDistance) {
        this.heuristicDistance = heuristicDistance;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getCost() {
        return cost;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public double getPriority() {
        return priority;
    }

    public void cameFrom(Node current) {
        this.cameFrom = current;
    }

    public Node getCameFrom() {
        return  cameFrom;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public boolean isMarked() {
        return marked;
    }
}
