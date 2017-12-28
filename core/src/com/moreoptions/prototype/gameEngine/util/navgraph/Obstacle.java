package com.moreoptions.prototype.gameEngine.util.navgraph;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.moreoptions.prototype.gameEngine.components.CollisionComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.SquareCollisionComponent;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by denwe on 27.12.2017.
 */

public class Obstacle extends Rectangle {

    private Entity entity;
    private ArrayList<Node>nodes = new ArrayList<Node>();

    public Obstacle(int dimension, Entity entity) {
        super(entity.getComponent(SquareCollisionComponent.class).getHitbox());
        this.entity = entity;

        init(dimension);
        generateNodes();
    }

    private void init(int dimension) {
        this.setX(getX() - dimension);
        this.setY(getY() - dimension);
        this.setWidth(getWidth() + 2 * dimension);
        this.setHeight(getHeight() + 2 * dimension);
    }

    private void generateNodes() {
        nodes.add(new Node(x - 1, y - 1));
        nodes.add(new Node(x - 1, y + height + 1));
        nodes.add(new Node(x + width + 1, y - 1));
        nodes.add(new Node(x + width + 1, y + height + 1));
    }

    public void update() {
        float x = entity.getComponent(CollisionComponent.class).getOldX() - entity.getComponent(PositionComponent.class).getX();
        float y = entity.getComponent(CollisionComponent.class).getOldY() - entity.getComponent(PositionComponent.class).getY();

        if (x != 0 || y != 0) {      // We moved. Update nodes.

        }

    }


    public Entity getEntity() {
        return entity;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
}

