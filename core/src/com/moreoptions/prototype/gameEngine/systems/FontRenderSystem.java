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
import com.moreoptions.prototype.gameEngine.components.TimedComponent;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventListener;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventSubscriber;

/**
 * Created by denwe on 09.11.2017.
 */
public class FontRenderSystem extends EntitySystem {

    private SpriteBatch batch;
    private BitmapFont font;
    private Family f = Family.all(CombatTextComponent.class).get();

    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<CombatTextComponent> ctMapper = ComponentMapper.getFor(CombatTextComponent.class);

    private EventSubscriber subscriber = new EventSubscriber();

    public FontRenderSystem(SpriteBatch batch) {
        this.batch = batch;
        this.font = new BitmapFont();

        subscriber.subscribe(Consts.DAMAGE_COMBAT_TEXT_EVENT, new EventListener() {
            @Override
            public boolean trigger(Event e) {
                float amount = e.getData(Consts.DAMAGE_AMOUNT, Float.class);
                Entity hit = e.getData(Consts.HIT, Entity.class);




                Entity combatTextEntity = new Entity();
                combatTextEntity.add(new CombatTextComponent(""+amount));
                combatTextEntity.add(new PositionComponent(posMapper.get(hit).getPosition().cpy()));
                combatTextEntity.add(new TimedComponent(1f));
                getEngine().addEntity(combatTextEntity);



                return true;
            }
        });

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
