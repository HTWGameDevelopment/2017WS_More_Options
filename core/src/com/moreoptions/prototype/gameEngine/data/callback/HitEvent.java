package com.moreoptions.prototype.gameEngine.data.callback;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.util.EventFactory;

/**
 * Created by Dennis on 06.12.2017.
 */
public interface HitEvent {
    boolean onHit(Entity self, Entity hit);

    class StandardHitEvent implements HitEvent {
        private ComponentMapper<StatsComponent> statsMapper = ComponentMapper.getFor(StatsComponent.class);

        @Override
        public boolean onHit(Entity self, Entity hit) {

            if(statsMapper.has(hit)) {
                EventFactory.projectileHit(self,hit);

            } else {
                return false;
            }
            return true;
        }
    }

}
