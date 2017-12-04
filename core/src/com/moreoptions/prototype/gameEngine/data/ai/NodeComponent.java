package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;

/**
 * Created by denwe on 03.12.2017.
 */
public class NodeComponent implements Component {

    Node n;


    public Node init(Entity entity) {

        n = new Node(entity.getComponent(PositionComponent.class).getX(),entity.getComponent(PositionComponent.class).getY());
        return n;
    }
}
