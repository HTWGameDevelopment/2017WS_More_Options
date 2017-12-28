package com.moreoptions.prototype.gameEngine.util.navgraph;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moreoptions.prototype.gameEngine.data.pathfinding.Path;

/**
 * Created by denwe on 24.12.2017.
 */
public interface NavGraph {

    boolean addEntity(Entity e);
    boolean removeEntity(Entity e);
    boolean updateEntity(Entity e);

    Path getPath(Entity start, Entity target);
    Path getPath(Entity start, float endX, float endY);
    Path getPath(float startX, float startY, float endX, float endY);
    Path getPath(float startX, float startY, Entity target);


    void draw(ShapeRenderer renderer, SpriteBatch batch, BitmapFont font);

}
