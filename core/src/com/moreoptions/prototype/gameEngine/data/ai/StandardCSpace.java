package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.CollisionComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.SquareCollisionComponent;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.util.CollisionUtil;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Andreas on 30.11.2017.
 */
public class StandardCSpace implements CSpace {

    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<CSpaceRectangle> cSpaceRectangles = new ArrayList<CSpaceRectangle>();


    @Override
    public void update(Room room, Entity e) {

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
