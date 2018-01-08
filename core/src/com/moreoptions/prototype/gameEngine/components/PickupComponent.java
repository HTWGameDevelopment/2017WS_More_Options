package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.callback.PickupEvent;

/**
 * Component for PickUps
 */
public class PickupComponent implements Component {

    private PickupEvent event;
    private Room room;

    public PickupComponent(PickupEvent event, Room room) {
        this.event = event;this.room = room;
    }

    public boolean trigger(Entity e) {
        return event.onPickup(e);
    }


    public Room getRoom() {
        return room;
    }
}
