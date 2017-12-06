package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.AIComponent;

/**
 * Created by denwe on 04.12.2017.
 */
public class AISystem extends EntitySystem {

    Family f = Family.all(AIComponent.class).get();
    ShapeRenderer renderer;

    public AISystem(ShapeRenderer renderer) {
        this.renderer =renderer;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(f);
        for(Entity e : entities) {
            e.getComponent(AIComponent.class).getState().update(GameWorld.getInstance().getRoomManager().getCurrentRoom(), e, deltaTime);
            e.getComponent(AIComponent.class).getState().draw(renderer);
        }
        renderer.end();

    }
}
