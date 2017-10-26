package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moreoptions.prototype.gameEngine.components.DebugColorComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;

/**
 * Basic ShapeRenderer
 */
public class DebugRenderSystem extends EntitySystem{

    private Family f = Family.all(DebugColorComponent.class).all(PositionComponent.class).get();
    private ShapeRenderer renderer = new ShapeRenderer();

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0,0,1,1);
        for(Entity e : getEngine().getEntitiesFor(f)) {
            PositionComponent p = e.getComponent(PositionComponent.class);
            DebugColorComponent dc = e.getComponent(DebugColorComponent.class);
            renderer.setColor(dc.getColor());
            renderer.circle(p.getX(),p.getY(),5);
        }
        renderer.end();
    }

}

