package com.moreoptions.prototype.gameEngine.data.pathfinding;

import java.util.ArrayList;

/**
 * Created by denwe on 08.12.2017.
 */
public class Path {


    private Node start;
    private Node end;
    boolean valid;
    private NavGraph graph;
    private ArrayList<Node> pathList;

    public Path(ArrayList<Node> pathList, NavGraph graph) {

        if(pathList.isEmpty()) {
            valid = false;
        } else {
            valid = true;
            start = pathList.get(0);
            end = pathList.get(pathList.size()-1);
            this.pathList = pathList;
            this.graph = graph;
        }
    }

    public boolean checkConnection() {
        return graph.checkPath(this);
    }


    public boolean isValid() {
        return valid;
    }

    public Node getNext() {
        return start;
    }

    public ArrayList<Node> getNodes() {
        return pathList;
    }
}
