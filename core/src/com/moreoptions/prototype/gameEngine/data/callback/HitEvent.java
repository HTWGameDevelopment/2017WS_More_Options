package com.moreoptions.prototype.gameEngine.data.callback;

import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.*;

/**
 * Created by Dennis on 06.12.2017.
 */
public interface HitEvent {

    boolean onHit(Entity self, Entity hit);

    class StandardHitEvent implements HitEvent {

        @Override
        public boolean onHit(Entity self, Entity hit) {

            float currentHealth = hit.getComponent(EnemyComponent.class).getCurrentHealth();
            float dmg = self.getComponent(ProjectileComponent.class).getDmg();
            hit.getComponent(EnemyComponent.class).setCurrentHealth( currentHealth - dmg);
            System.out.println("hit");

            //TODO: Make a function out of this
            Entity dmgText = new Entity();
            dmgText.add(new PositionComponent(hit.getComponent(PositionComponent.class).getX(),hit.getComponent(PositionComponent.class).getY()+3));
            dmgText.add(new CombatTextComponent("-" + dmg + "!!!"));
            dmgText.add(new TimedComponent(0.5f));
            GameWorld.getInstance().addEntity(dmgText);
            return true;
        }
    }

}
