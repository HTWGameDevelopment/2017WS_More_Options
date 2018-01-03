package com.moreoptions.prototype.gameEngine.data.callback;

import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.GameWorld;

public interface OnDeathEvent {

    boolean onDeath(Entity us, Entity them);

}
