package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.moreoptions.prototype.gameEngine.data.ai.Node;

/**
 * Created by denwe on 02.12.2017.
 */
public class NavigationComponent implements Component {


    Node node;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
