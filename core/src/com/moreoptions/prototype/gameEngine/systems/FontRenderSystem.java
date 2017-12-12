package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
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

    private SpriteBatch batch;
    private BitmapFont font;
    private Family f = Family.all(CombatTextComponent.class).get();

    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<CombatTextComponent> ctMapper = ComponentMapper.getFor(CombatTextComponent.class);

    public FontRenderSystem(SpriteBatch batch) {
        this.batch = batch;
        this.font = new BitmapFont();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        ImmutableArray<Entity> entites = getEngine().getEntitiesFor(f);
        batch.begin();
        for(Entity e : entites) {
            CombatTextComponent c = ctMapper.get(e);
            PositionComponent p = posMapper.get(e);
            font.draw(batch,c.getText(),p.getX(),p.getY());
        }
        batch.end();
    }

}
