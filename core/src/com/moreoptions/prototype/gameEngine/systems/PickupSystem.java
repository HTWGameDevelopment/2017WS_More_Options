package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.util.EventFactory;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventBus;

/**
 * System that handles pickupcomponents
 */
public class PickupSystem extends EntitySystem {

    private Family f = Family.all(PickupComponent.class, PositionComponent.class, CollisionComponent.class, CircleCollisionComponent.class).get();
    private Family p = Family.all(PlayerComponent.class).get();

    private ComponentMapper<CircleCollisionComponent> cmapper = ComponentMapper.getFor(CircleCollisionComponent.class);
    private ComponentMapper<PickupComponent> pucMapper = ComponentMapper.getFor(PickupComponent.class);

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        ImmutableArray<Entity> pickups = getEngine().getEntitiesFor(f);
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(p);

        for(Entity p : players) {
            for(Entity pickup : pickups) {
                if(cmapper.get(p).getHitbox().overlaps(cmapper.get(pickup).getHitbox())) {
                    PickupComponent pickupComponent = pucMapper.get(pickup);
                    if(pickupComponent.isShopItem()) {
                        Statistics pStats = p.getComponent(StatsComponent.class).getStats();

                        if(pStats.getMoney() >= pickupComponent.getPrice()) {
                            pStats.setMoney( pStats.getMoney() - pickupComponent.getPrice());
                            if(pickupComponent.trigger(p)) {
                                pickupComponent.getRoom().removePickup(pickup);
                                getEngine().removeEntity(pickup);
                                CombatTextComponent cp = new CombatTextComponent(pickupComponent.getName() + " was picked up!");
                                TimedComponent timedComponent = new TimedComponent(2);
                                Entity itemText = new Entity();
                                itemText.add(cp);
                                itemText.add(timedComponent);

                                itemText.add(new PositionComponent(pickup.getComponent(PositionComponent.class).getPosition().cpy()));
                                getEngine().addEntity(itemText);
                                EventFactory.playSound(Consts.Sound.PURCHASE_ITEM);
                            }
                        }

                    } else
                    if(pickupComponent.trigger(p)) {
                        pickupComponent.getRoom().removePickup(pickup);
                        getEngine().removeEntity(pickup);
                        CombatTextComponent cp = new CombatTextComponent(pickupComponent.getName() + " was picked up!");
                        TimedComponent timedComponent = new TimedComponent(2);
                        Entity itemText = new Entity();
                        itemText.add(cp);
                        itemText.add(timedComponent);

                        itemText.add(new PositionComponent(pickup.getComponent(PositionComponent.class).getPosition().cpy()));
                        getEngine().addEntity(itemText);
                    }
                }
            }
        }

    }
}
