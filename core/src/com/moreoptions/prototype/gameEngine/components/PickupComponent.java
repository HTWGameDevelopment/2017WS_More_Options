package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.data.callback.PickupEvent;

/**
 * Component for PickUps
 */
public class PickupComponent implements Component {

    private PickupEvent event;

    public PickupComponent(PickupEvent event) {
        this.event = event;
    }

    public boolean trigger(Entity e) {
        return event.onPickup(e);
    }

}
