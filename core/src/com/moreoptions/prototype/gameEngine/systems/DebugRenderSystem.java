package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;

/**
 * Created by Dennis on 23.10.2017.
 */
public class DebugRenderSystem extends EntitySystem{

    Family f = Family.all(PositionComponent.class).get();
    ShapeRenderer renderer = new ShapeRenderer();




    //hard implementation, drawing a 5 radius circle around every entity with a positionComponent
    //TODO: implement this properly. this implementation is just for fast demonstration
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0,0,1,1);
        for(Entity e : getEngine().getEntitiesFor(f)) {
            PositionComponent p = e.getComponent(PositionComponent.class);
            renderer.circle(p.getX(),p.getY(),5);
        }
        renderer.end();
    }



}

