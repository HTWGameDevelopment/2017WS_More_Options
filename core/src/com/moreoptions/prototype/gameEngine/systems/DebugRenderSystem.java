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
            .exclude(EnemyComponent.class)
            .get();

    private Family enemies = Family.all(EnemyComponent.class,PositionComponent.class,CollisionComponent.class).get();

    private ShapeRenderer renderer;

    private Family debugLines = Family.all(DebugLineComponent.class)
            .get();
    private Family debugCircles = Family.all(DebugCircleComponent.class).exclude(EnemyComponent.class)
            .get();

    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<DebugLineComponent> cm = ComponentMapper.getFor(DebugLineComponent.class);
    private ComponentMapper<DebugCircleComponent> am = ComponentMapper.getFor(DebugCircleComponent.class);
    private ComponentMapper<SquareCollisionComponent> sqcMapper = ComponentMapper.getFor(SquareCollisionComponent.class);
    private ComponentMapper<CircleCollisionComponent> cccMapper = ComponentMapper.getFor(CircleCollisionComponent.class);
    private ComponentMapper<DebugColorComponent> dcolorMapper = ComponentMapper.getFor(DebugColorComponent.class);
    private ComponentMapper<DebugCircleComponent> dccMapper = ComponentMapper.getFor(DebugCircleComponent.class);
    private ComponentMapper<DebugSquareComponent> dscMapper = ComponentMapper.getFor(DebugSquareComponent.class);


    public DebugRenderSystem(ShapeRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0,0,1,1);
        for(Entity e : getEngine().getEntitiesFor(f)) {
            DebugColorComponent dc = dcolorMapper.get(e);


                if (dc != null) renderer.setColor(dc.getColor());
                else renderer.setColor(Color.FIREBRICK);
                if (sqcMapper.has(e)) {
                    Rectangle hitbox = sqcMapper.get(e).getHitbox();
                    renderer.rect(hitbox.x, hitbox.y, hitbox.getWidth(), hitbox.getHeight());
                } else if (cccMapper.has(e)) {
                    Circle c = cccMapper.get(e).getHitbox();
                    renderer.circle(c.x, c.y, c.radius);
                }

        }

        renderer.end();
        renderer.begin(ShapeRenderer.ShapeType.Line);

        for(Entity e : getEngine().getEntitiesFor(f)) {
            renderer.setColor(Color.CYAN);

            if(sqcMapper.has(e)) {
                Rectangle hitbox = sqcMapper.get(e).getHitbox();
                renderer.rect(hitbox.x,hitbox.y,hitbox.getWidth(),hitbox.getHeight());
            } else if(cccMapper.has(e)) {
                Circle c = cccMapper.get(e).getHitbox();
                renderer.circle(c.x,c.y,c.radius);
            }
        }

        drawDebugLines(renderer);

        renderer.end();
        drawEnemies(renderer);
        drawDebugCircle(renderer);


    }

    private void drawEnemies(ShapeRenderer renderer) {

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        for(Entity e : getEngine().getEntitiesFor(enemies)) {
            EnemyComponent ec = e.getComponent(EnemyComponent.class);
            if(ec.isDead()) continue;

            DebugColorComponent dcc = dcolorMapper.get(e);
            DebugCircleComponent dlc = dccMapper.get(e);
            DebugSquareComponent dsc = dscMapper.get(e);
            PositionComponent pc = posMapper.get(e);
            renderer.setColor(dcc.getColor());
            if (dccMapper.has(e)) renderer.circle(pc.getX(),pc.getY(),dlc.getRadius());
            if (dscMapper.has(e)) renderer.rect(pc.getX(),pc.getY(),dsc.getSize(),dsc.getSize());
        }
        renderer.end();
    }

    private void drawDebugLines(ShapeRenderer renderer) {
        renderer.setColor(Color.RED);
        for(Entity e : getEngine().getEntitiesFor(debugLines)) {
            renderer.line(cm.get(e).getStart(), cm.get(e).getEnd());
        }
    }

    private void drawDebugCircle(ShapeRenderer renderer) {
        renderer.setColor(Color.BROWN);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        for(Entity e : getEngine().getEntitiesFor(debugCircles)) {
            PositionComponent pc = posMapper.get(e);
            DebugCircleComponent cc = dccMapper.get(e);
            renderer.circle(pc.getX(),pc.getY(), cc.getRadius());
        }
        renderer.end();
    }
}

