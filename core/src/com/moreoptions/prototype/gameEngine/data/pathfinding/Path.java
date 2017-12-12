package com.moreoptions.prototype.gameEngine.data.pathfinding;

import java.util.ArrayList;

/**
 * Created by denwe on 08.12.2017.
 */
public class Path {


    Node start;
    Node end;

    public Path(ArrayList<Node> pathList) {

        float distance = 0;

        for(Node n : pathList) {
            if(n.getCameFrom() == null) start = n;
        }


    }



}
