package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.moreoptions.prototype.gameEngine.components.TimedComponent;

/**
 * Created by denwe on 03.11.2017.
 */
public class TimedSystem extends EntitySystem {

    Family f = Family.all(TimedComponent.class).get();
    ComponentMapper<TimedComponent> cm = ComponentMapper.getFor(TimedComponent.class);
    @Override
    public void update(float deltaTime) {

        for(Entity e : getEngine().getEntitiesFor(f)) {
            TimedComponent t = cm.get(e);
            t.setCurrentTime(t.getCurrentTime() + deltaTime);

            if(t.getCurrentTime() > t.getTime()) {
                getEngine().removeEntity(e);
            }
        }

    }
}
