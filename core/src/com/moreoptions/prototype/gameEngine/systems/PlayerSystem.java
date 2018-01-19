package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.util.DataTracker;
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
    ProgressBar healthbar;

    public PlayerSystem(ProgressBar healthBar) {
        this.healthbar = healthBar;
        subscriber = new EventSubscriber();
        subscriber.subscribe(Consts.DAMAGE_EVENT, new EventListener() {
            @Override
            public boolean trigger(Event e) {
                Entity hit = e.getData(Consts.HIT, Entity.class);
                Entity hitter = e.getData(Consts.SELF, Entity.class);

                Statistics statistics = scMapper.get(hit).getStats();
                Statistics enemystatistics = scMapper.get(hitter).getStats();
                if(statistics.getImmunityTimer() <= statistics.getTimeSinceLastHit()) {
                    statistics.setCurrentHealth(statistics.getCurrentHealth() - enemystatistics.getDamage());
                    DataTracker.trackIntData(Consts.Data.DAMAGE_TAKEN, (int)enemystatistics.getDamage());
                    EventFactory.createDamageText(hit, enemystatistics.getDamage());

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
            Statistics s = scMapper.get(p).getStats();

            if(s.getTimeSinceLastHit() < s.getImmunityTimer()) {
                p.getComponent(DisplacableComponent.class).setImmune(true);
            } else {
                p.getComponent(DisplacableComponent.class).setImmune(false);
            }

            if(isDead(scMapper.get(p))) {

                EventFactory.gameOver();

            };

            healthbar.setValue(s.getCurrentHealth());
            healthbar.setRange(0, s.getMaxHealth());

        }




    }

    private boolean isDead(StatsComponent statsComponent) {
        return statsComponent.getStats().getCurrentHealth() <= 0;
    }


}
