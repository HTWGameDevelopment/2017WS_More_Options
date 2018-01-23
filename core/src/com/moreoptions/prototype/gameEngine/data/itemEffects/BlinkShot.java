package com.moreoptions.prototype.gameEngine.data.itemEffects;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.ProjectileComponent;
import com.moreoptions.prototype.gameEngine.components.StatsComponent;
import com.moreoptions.prototype.gameEngine.data.callback.HitEvent;
import com.moreoptions.prototype.gameEngine.util.EventFactory;

public class BlinkShot implements HitEvent {


    private ComponentMapper<StatsComponent> statsMapper = ComponentMapper.getFor(StatsComponent.class);
    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);

    @Override
    public boolean onHit(Entity self, Entity hit) {


        if(statsMapper.has(hit)) {

            Entity p = self.getComponent(ProjectileComponent.class).getOwner();



            PositionComponent temp1 = posMapper.get(p);
            PositionComponent temp2 = posMapper.get(hit);
            Vector2 v1 = temp1.getPosition().cpy();
            Vector2 v2 = temp2.getPosition().cpy();

            EventFactory.projectileHit(self,hit);
            if(hit.getComponent(StatsComponent.class).getStats().getCurrentHealth() < 1) {

                temp1.setPosition(v2);
                temp2.setPosition(v1);
            }
        } else {


            return false;
        }
        return true;
    }
}

