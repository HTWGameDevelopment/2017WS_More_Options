package com.moreoptions.prototype.gameEngine.data.callback;

import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.GameWorld;

public interface GameEvent {

    boolean onTrigger(Entity us, Entity them);

    public class NullEvent implements GameEvent {
        @Override
        public boolean onTrigger(Entity us, Entity them) {
            return false;
        }
    }
}
