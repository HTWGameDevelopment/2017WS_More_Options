package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.TileGraphicComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class TileRenderSystem extends EntitySystem{

    Family tiles = Family.all(TileGraphicComponent.class).get();
    SpriteBatch tilebatch;

    public TileRenderSystem(SpriteBatch batch) {

        this.tilebatch = batch;


    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        ImmutableArray<Entity> e = getEngine().getEntitiesFor(tiles);

        ArrayList<Entity> sorted = new ArrayList<Entity>();
        sorted.addAll(Arrays.asList(e.toArray()));

        sorted.sort(new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {

                if(o1.getComponent(TileGraphicComponent.class).getZIndex() > o2.getComponent(TileGraphicComponent.class).getZIndex()) return 1;
                if(o1.getComponent(TileGraphicComponent.class).getZIndex() < o2.getComponent(TileGraphicComponent.class).getZIndex()) return -1;


                return 0;
            }
        });



        tilebatch.begin();
        for(Entity ex : sorted) {
            TextureRegion rx = ex.getComponent(TileGraphicComponent.class).getTextureRegion();
            PositionComponent p = ex.getComponent(PositionComponent.class);



            tilebatch.draw(rx,p.getX(),p.getY());
        }
        tilebatch.end();

    }
}
