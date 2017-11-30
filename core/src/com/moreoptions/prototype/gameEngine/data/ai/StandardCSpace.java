package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.moreoptions.prototype.gameEngine.components.SquareCollisionComponent;
import com.moreoptions.prototype.gameEngine.data.Room;

import java.util.ArrayList;



/**
 * Created by Andreas on 30.11.2017.
 */
public class StandardCSpace implements CSpace {

    private ArrayList<Node> nodes;
    private ArrayList<Rectangle> rectangles;

    @Override
    public void update(Room room, Entity e) {
        ArrayList<Entity> entities = new ArrayList<Entity>();

        entities.addAll(room.getDestructibleEntities());
        entities.addAll(room.getTileEntities());

        for (Entity x : entities) {
            rectangles.add(translateToCSpace(e, x));
        }
    }

    private Rectangle translateToCSpace(Entity e, Entity x) {

        SquareCollisionComponent scc = x.getComponent(SquareCollisionComponent.class);
        Rectangle rect = scc.getHitbox();
        CSpaceRectangle cRect = new Rectangle();


        return ;
    }

    @Override
    public ArrayList<Node> getNodes() {
        return null;
    }

    @Override
    public ArrayList<Rectangle> getRectangles() {
        return null;
    }
}
