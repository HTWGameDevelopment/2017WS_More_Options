package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.util.navgraph.Node;
import com.moreoptions.prototype.gameEngine.util.navgraph.Path;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by denwe on 17.12.2017.
 */
public class NavigationComponent implements Component {

    Entity self;
    Path path;
    Node node;

    public NavigationComponent(Entity self) {
        this.self = self;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void addNodes(ArrayList<Node> nodes) {
        nodes.addAll(nodes);
    }


    public Node getNode() {
        return node;
    }
}
