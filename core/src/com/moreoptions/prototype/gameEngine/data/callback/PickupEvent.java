package com.moreoptions.prototype.gameEngine.data.callback;

import com.badlogic.ashley.core.Entity;

/**
 * Listener interface for Pickups
 */
public interface PickupEvent {

    boolean onPickup(Entity e);

}