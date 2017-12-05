package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
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
    private ComponentMapper<SquareCollisionComponent> sqm = ComponentMapper.getFor(SquareCollisionComponent.class);
    private ComponentMapper<CircleCollisionComponent> ccc = ComponentMapper.getFor(CircleCollisionComponent.class);


    public DebugRenderSystem(ShapeRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0,0,1,1);
        for(Entity e : getEngine().getEntitiesFor(f)) {
            DebugColorComponent dc = e.getComponent(DebugColorComponent.class);

            if(dc!= null) renderer.setColor(dc.getColor());
            else renderer.setColor(Color.FIREBRICK);
            if(sqm.has(e)) {
                Rectangle hitbox = sqm.get(e).getHitbox();
                renderer.rect(hitbox.x,hitbox.y,hitbox.getWidth(),hitbox.getHeight());
            } else if(ccc.has(e)) {
                Circle c = ccc.get(e).getHitbox();
                renderer.circle(c.x,c.y,c.radius);
            }
        }

        renderer.end();
        renderer.begin(ShapeRenderer.ShapeType.Line);

        for(Entity e : getEngine().getEntitiesFor(f)) {
            renderer.setColor(Color.CYAN);

            if(sqm.has(e)) {
                Rectangle hitbox = sqm.get(e).getHitbox();
                renderer.rect(hitbox.x,hitbox.y,hitbox.getWidth(),hitbox.getHeight());
            } else if(ccc.has(e)) {
                Circle c = ccc.get(e).getHitbox();
                renderer.circle(c.x,c.y,c.radius);
            }
        }

        drawDebugLines(renderer);
        drawDebugCircle(renderer);

        renderer.end();
    }

    private void drawDebugLines(ShapeRenderer renderer) {
        renderer.setColor(Color.RED);
        for(Entity e : getEngine().getEntitiesFor(debugLines)) {
            renderer.line(cm.get(e).getStart(), cm.get(e).getEnd());
        }
    }

    private void drawDebugCircle(ShapeRenderer renderer) {
        renderer.setColor(Color.RED);
        for(Entity e : getEngine().getEntitiesFor(debugCircles)) {
            renderer.circle(am.get(e).getCenter().x,am.get(e).getCenter().y, am.get(e).getRadius());
        }
    }
}

