package com.moreoptions.prototype.gameEngine.data.callback;

import com.badlogic.ashley.core.Entity;

/**
 * Created by denwe on 01.11.2017.
 */
public interface EntityCollisionListener {

    boolean onCollision(Entity e);

}
