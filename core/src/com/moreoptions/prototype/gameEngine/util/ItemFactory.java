package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.data.callback.PickupEvent;
import com.moreoptions.prototype.level.layers.EnemyLayer;

/**
 * Created by Dennis on 02.01.2018.
 */
public class ItemFactory {



    public static Entity getItem(float x, float y, int id, EnemyLayer enemyLayer) {
        Entity item = new Entity();
        item.add(new PositionComponent(x,y));
        item.add(new DebugColorComponent(Color.BROWN));
        item.add(new CollisionComponent());
        item.add(new CircleCollisionComponent(x,y, 10));
        item.add(new DebugCircleComponent(10));
        item.add(getPickupComponent(id));





        return item;


    }


    private static PickupComponent getPickupComponent(int id) {
        switch (id) {
            default:
                PickupComponent p  = new PickupComponent(new PickupEvent() {
                    @Override
                    public boolean onPickup(Entity e) {

                        Statistics stats = e.getComponent(StatsComponent.class).getStats();

                        stats.setSpeed(stats.getSpeed() * 2);

                        return true;
                    }
                });
                return p;

        }
    }


}
