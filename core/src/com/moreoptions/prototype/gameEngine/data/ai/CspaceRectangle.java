package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.moreoptions.prototype.gameEngine.components.CircleCollisionComponent;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Andreas on 30.11.2017.
 */
public class CSpaceRectangle extends Rectangle {

    Entity entity;
    private Collection<? extends Node> nodes;

    public CSpaceRectangle(Entity e, Entity entity, Rectangle rect) {
        super(rect);
        this.entity = entity;
        this.set(rect);

        update(e);
    }

    public void update(Entity e) {
        CircleCollisionComponent ccc = e.getComponent(CircleCollisionComponent.class);
        System.out.println("TESTSHIT" +getX());
        this.setX(getX() - ccc.getHitbox().radius);
        this.setY(getY() - ccc.getHitbox().radius);
        this.setWidth(getWidth() + 2 * ccc.getHitbox().radius);
        this.setHeight(getHeight() + 2 * ccc.getHitbox().radius);
    }

    public Entity getEntity() {
        return entity;
    }

    public Collection<? extends Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(x, y));
        nodes.add(new Node(x, y + height));
        nodes.add(new Node(x + width, y));
        nodes.add(new Node(x + width, y + height));
        return nodes;
    }
}
