package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moreoptions.prototype.gameEngine.components.CollisionComponent;
import com.moreoptions.prototype.gameEngine.components.DebugColorComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;

/**
 * Basic ShapeRenderer
 */
public class DebugRenderSystem extends EntitySystem{

    private Family f = Family.all(DebugColorComponent.class)
            .all(PositionComponent.class)
            .all(CollisionComponent.class)
            .get();
    private ShapeRenderer renderer;

    public DebugRenderSystem(ShapeRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0,0,1,1);
        for(Entity e : getEngine().getEntitiesFor(f)) {
            PositionComponent p = e.getComponent(PositionComponent.class);
            DebugColorComponent dc = e.getComponent(DebugColorComponent.class);
            CollisionComponent cc = e.getComponent(CollisionComponent.class);

            renderer.setColor(dc.getColor());

            switch(cc.getShape()) {
                case CIRCLE:
                    renderer.circle(p.getX(),p.getY(),cc.getSize());
                    break;
                case RECTANGLE:
                    renderer.rect(p.getX(),p.getY(),cc.getSize(),cc.getSize());
                    break;
            }
        }
        renderer.end();
        renderer.begin(ShapeRenderer.ShapeType.Line);

        for(Entity e : getEngine().getEntitiesFor(f)) {
            PositionComponent p = e.getComponent(PositionComponent.class);
            CollisionComponent cc = e.getComponent(CollisionComponent.class);

            renderer.setColor(Color.CYAN);

            switch(cc.getShape()) {
                case CIRCLE:
                    renderer.circle(p.getX(),p.getY(),cc.getSize());
                    break;
                case RECTANGLE:
                    renderer.rect(p.getX(),p.getY(),cc.getSize(),cc.getSize());
                    break;
            }
        }


        renderer.end();
    }

}

