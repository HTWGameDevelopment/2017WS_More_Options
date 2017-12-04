package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.moreoptions.prototype.gameEngine.components.CircleCollisionComponent;
import com.moreoptions.prototype.gameEngine.components.CollisionComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Andreas on 30.11.2017.
 */
public class CSpaceRectangle extends Rectangle {

    private Entity entity;
    private ArrayList<Node> nodes = new ArrayList<Node>();
    public CSpaceRectangle(int dimension, Entity entity, Rectangle rect) {
        super(rect);
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
        nodes.add(new Node(x-1, y-1));
        nodes.add(new Node(x-1, y + height+1));
        nodes.add(new Node(x + width+1, y-1));
        nodes.add(new Node(x + width+1, y + height+1));
    }

    public void update() {
        float x = entity.getComponent(CollisionComponent.class).getOldX() - entity.getComponent(PositionComponent.class).getX();
        float y = entity.getComponent(CollisionComponent.class).getOldY() - entity.getComponent(PositionComponent.class).getY();

        if(x != 0 || y != 0) {      // We moved. Update nodes.

        }

    }



    public Entity getEntity() {
        return entity;
    }

    public Collection<? extends Node> getNodes() {
        return nodes;
    }
}
