package com.moreoptions.prototype.gameEngine.data.itemEffects;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.DisplacableComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.ProjectileComponent;
import com.moreoptions.prototype.gameEngine.data.callback.HitEvent;
import com.moreoptions.prototype.gameEngine.util.EventFactory;

public class KnockBack implements HitEvent {


    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);


    @Override
    public boolean onHit(Entity self, Entity hit) {
        Entity p = self.getComponent(ProjectileComponent.class).getOwner();

        PositionComponent temp1 = posMapper.get(p);
        PositionComponent temp2 = posMapper.get(hit);
        float xEnm = temp2.getX();
        float yEnm = temp2.getY();
        float xPla = temp1.getX();
        float yPla = temp1.getY();
        Vector2 v2 = new Vector2(xEnm - xPla, yEnm - yPla);
        v2 = v2.nor();

        hit.getComponent(DisplacableComponent.class).applyForce(v2, 6);


        EventFactory.projectileHit(self, hit);

        return true;
    }
}
