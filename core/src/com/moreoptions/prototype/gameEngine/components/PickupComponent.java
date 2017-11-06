package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/**
 * Created by denwe on 06.11.2017.
 */
public class PickupComponent implements Component {

    PickupEvent event;

    public PickupComponent(PickupEvent event) {
        this.event = event;
    }

    public void trigger(Entity e) {
        event.onPickup(e);
        e.getComponent(PositionComponent.class).setX(100);
        e.getComponent(PositionComponent.class).setY(100);
    }


    public interface PickupEvent {

        void onPickup(Entity e);

    }
}
