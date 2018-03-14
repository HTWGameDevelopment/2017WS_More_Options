package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.TileGraphicComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class TileRenderSystem extends EntitySystem{

    private Family tiles = Family.all(TileGraphicComponent.class).get();
    private SpriteBatch tilebatch;

    private ComponentMapper<TileGraphicComponent> tgcMapper = ComponentMapper.getFor(TileGraphicComponent.class);
    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);

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

                if(tgcMapper.get(o1).getZIndex() > tgcMapper.get(o2).getZIndex()) return 1;
                if(tgcMapper.get(o1).getZIndex() < tgcMapper.get(o2).getZIndex()) return -1;


                return 0;
            }
        });

        tilebatch.begin();
        for(Entity ex : sorted) {
            TextureRegion rx = ex.getComponent(TileGraphicComponent.class).getTextureRegion();

            PositionComponent p = posMapper.get(ex);
            if(rx != null) tilebatch.draw(rx,p.getX(),p.getY());
        }
        tilebatch.end();

    }
}
