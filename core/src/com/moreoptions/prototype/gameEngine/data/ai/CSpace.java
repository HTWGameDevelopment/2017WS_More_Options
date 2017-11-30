package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.moreoptions.prototype.gameEngine.data.Room;

import java.util.ArrayList;

/**
 * Created by Andreas on 30.11.2017.
 */
public interface CSpace {
    void update(Room room, Entity e);

    ArrayList<Node> getNodes();
    ArrayList<Rectangle> getRectangles();



}
