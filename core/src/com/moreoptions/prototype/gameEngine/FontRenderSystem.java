package com.moreoptions.prototype.gameEngine;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.moreoptions.prototype.gameEngine.components.CombatTextComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;

/**
 * Created by denwe on 09.11.2017.
 */
public class FontRenderSystem extends EntitySystem {

    SpriteBatch batch;
    BitmapFont font;
    Family f = Family.all(CombatTextComponent.class).get();

    public FontRenderSystem(SpriteBatch batch, BitmapFont font) {
        this.batch = batch;
        this.font = font;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        ImmutableArray<Entity> entites = getEngine().getEntitiesFor(f);
        batch.begin();
        for(Entity e : entites) {
            CombatTextComponent c = e.getComponent(CombatTextComponent.class);
            PositionComponent p = e.getComponent(PositionComponent.class);
            font.draw(batch,c.getText(),p.getX(),p.getY());

        }
        batch.end();


    }
}
