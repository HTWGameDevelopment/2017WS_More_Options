package com.moreoptions.prototype;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by Dennis on 23.10.2017.
 */
public class InputSystem extends EntitySystem {


    Family family = Family.all(InputComponent.class).get();

    @Override
    public void update(float deltaTime) {

        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(family);

        for(Entity e : entities) {

            VelocityComponent v = e.getComponent(VelocityComponent.class);
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                v.velY = 1;
            } else {
                v.velY = 0;
            }
        }

    }
}
