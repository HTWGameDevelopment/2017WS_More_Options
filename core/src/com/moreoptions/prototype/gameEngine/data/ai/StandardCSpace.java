package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.moreoptions.prototype.gameEngine.data.Room;

import java.util.ArrayList;

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
