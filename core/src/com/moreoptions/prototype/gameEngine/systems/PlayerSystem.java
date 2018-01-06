package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.moreoptions.prototype.gameEngine.components.PlayerComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.ProjectileComponent;
import com.moreoptions.prototype.gameEngine.components.StatsComponent;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.util.EventFactory;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventListener;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventSubscriber;

/**
 * Created by Dennis on 06.12.2017.
 */
public class PlayerSystem extends EntitySystem{

    private Family f = Family.all(PlayerComponent.class).get();

    private ComponentMapper<StatsComponent> scMapper = ComponentMapper.getFor(StatsComponent.class);

    EventSubscriber subscriber;

    public PlayerSystem() {
        subscriber = new EventSubscriber();
        subscriber.subscribe(Consts.CONTACT_DAMAGE_EVENT, new EventListener() {
            @Override
            public boolean trigger(Event e) {
                Entity hit = e.getData(Consts.SELF, Entity.class);

                Statistics statistics = scMapper.get(hit).getStats();
                if(statistics.getImmunityTimer() > statistics.getTimeSinceLastHit()) {
                    statistics.setCurrentHealth(statistics.getCurrentHealth() - 1);
                    System.out.println("DamageEvent");

                    if (GameState.getInstance().isDebugMode()) {
                        EventFactory.createDamageText(hit, 1);
                    }
                    statistics.setTimeSinceLastHit(0);
                }

                return true;
            }
        });
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        ImmutableArray<Entity> players = getEngine().getEntitiesFor(f);

        for(Entity p : players) {

            float x =  scMapper.get(p).getStats().getCurrentShotCooldown();
            scMapper.get(p).getStats().setCurrentShotCooldown(x+deltaTime);
            scMapper.get(p).getStats().setTimeSinceLastHit(scMapper.get(p).getStats().getTimeSinceLastHit() + deltaTime);

            if(isDead(scMapper.get(p))) {

                EventFactory.gameOver();

            };

        }




    }

    private boolean isDead(StatsComponent statsComponent) {
        return statsComponent.getStats().getCurrentHealth() <= 0;
    }


}
