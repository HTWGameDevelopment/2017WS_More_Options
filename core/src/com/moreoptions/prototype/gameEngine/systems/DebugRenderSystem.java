package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moreoptions.prototype.gameEngine.components.*;

/**
 * Basic ShapeRenderer
 */
public class DebugRenderSystem extends EntitySystem{

    private Family f = Family.all(DebugColorComponent.class)
            .all(PositionComponent.class)
            .all(CollisionComponent.class)
            .get();
    private ShapeRenderer renderer;

    private Family debugLines = Family.all(DebugLineComponent.class)
            .get();
    private Family debugCircles = Family.all(DebugCircleComponent.class)
            .get();

    private ComponentMapper<DebugLineComponent> cm = ComponentMapper.getFor(DebugLineComponent.class);
    private ComponentMapper<DebugCircleComponent> am = ComponentMapper.getFor(DebugCircleComponent.class);



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

        drawDebugLines(renderer);
        drawDebugCircle(renderer);


        renderer.end();
    }

    public void drawDebugLines(ShapeRenderer renderer) {

        renderer.setColor(Color.RED);
        for(Entity e : getEngine().getEntitiesFor(debugLines)) {
            renderer.line(cm.get(e).getStart(), cm.get(e).getEnd());
        }
    }

    public void drawDebugCircle(ShapeRenderer renderer) {

        renderer.setColor(Color.RED);
        for(Entity e : getEngine().getEntitiesFor(debugCircles)) {
            renderer.circle(am.get(e).getCenter().x,am.get(e).getCenter().y, am.get(e).getRadius());
        }
    }


}

