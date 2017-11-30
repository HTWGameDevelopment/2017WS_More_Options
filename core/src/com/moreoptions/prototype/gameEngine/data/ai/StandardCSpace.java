package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moreoptions.prototype.gameEngine.components.CollisionComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.SquareCollisionComponent;
import com.moreoptions.prototype.gameEngine.data.Room;

import java.util.ArrayList;

/**
 * Created by Andreas on 30.11.2017.
 */
public class StandardCSpace implements CSpace {

    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<CSpaceRectangle> cSpaceRectangles = new ArrayList<CSpaceRectangle>();

    public StandardCSpace(Room room, Entity e) {
        ArrayList<Entity> entities = new ArrayList<Entity>();

        entities.addAll(room.getDestructibleEntities());
        entities.addAll(room.getTileEntities());

        for (Entity x : entities) {
            cSpaceRectangles.add(translateToCSpace(e, x));
        }

        for (CSpaceRectangle cRect : cSpaceRectangles) {
            nodes.addAll(cRect.getNodes());
        }

        for(Node n : nodes) {
            for (CSpaceRectangle csr : cSpaceRectangles) {
                if (csr.contains(n.getX(), n.getY())) {
                    n.setBlocked(true);
                    n.setBlocker(csr.getEntity());
                    break;
                }
            }
        }

    }

    /**
     * update CSpace for every Entity in the room by checking whether the entities changed or not.
     * @param room the room the entities are in
     * @param e the entity the CSpace belongs to
     */
    @Override
    public void update(Room room, Entity e) {
        for (CSpaceRectangle cRect : cSpaceRectangles) {
            CollisionComponent cc = cRect.getEntity().getComponent(CollisionComponent.class);
            PositionComponent pc = cRect.getEntity().getComponent(PositionComponent.class);
            if (cc.getOldX() != pc.getX()
                    || cc.getOldY() != pc.getY()) {
                cRect.update(e);
            }
        }
    }

    /**
     * creates a CSpace rectangle of an entity.
     * @param e the entity the CSpace belongs to
     * @param x the entity from which the CSpace will be created
     * @return the created CSpaceRectangle
     */
    private CSpaceRectangle translateToCSpace(Entity e, Entity x) {

        SquareCollisionComponent scc = x.getComponent(SquareCollisionComponent.class);
        Rectangle rect = scc.getHitbox();

        return new CSpaceRectangle(e, x, rect);
    }

    @Override
    public ArrayList<Node> getNodes() {
        return null;
    }

    @Override
    public ArrayList<Rectangle> getRectangles() {
        return null;
    }

    public void debugDraw(ShapeRenderer renderer) {
        for (CSpaceRectangle cRect : cSpaceRectangles) {
            renderer.rect(cRect.getX(), cRect.getY(), cRect.getWidth(), cRect.getHeight());
        }

        for (Node n : nodes) {
            if (n.isBlocked()) {
                renderer.setColor(Color.GOLDENROD);
            } else {
                renderer.setColor(Color.CHARTREUSE);
            }

            renderer.circle(n.getX(), n.getY(), 5);
        }
    }
}
