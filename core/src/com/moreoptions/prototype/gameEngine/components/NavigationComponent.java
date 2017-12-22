package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.moreoptions.prototype.gameEngine.data.pathfinding.Node;

/**
 * Created by denwe on 17.12.2017.
 */
public class NavigationComponent implements Component {

    private Node n;

    public Node getNode() {
        return n;
    }

    public void setNode(Node n) {
        this.n = n;
    }
}
