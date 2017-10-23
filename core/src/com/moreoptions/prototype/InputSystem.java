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
    private static InputSystem instance = new InputSystem();

    public float speed = 5.0f;

    public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = false;

    public static InputSystem getInstance() {
        if(instance == null) {
            instance = new InputSystem();
        }
        return instance;
    }

    private InputSystem() {

    }

    Family family = Family.all(InputComponent.class).get();

    @Override
    public void update(float deltaTime) {

        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(family);

        for(Entity e : entities) {

            VelocityComponent v = e.getComponent(VelocityComponent.class);

            if (up && down) {
                v.velY = 0;
            } else if (up) {
                v.velY = speed;
            } else if (down) {
                v.velY = -speed;
            } else if (!up && !down) {
                v.velY = 0;
            }

            if (left && right) {
                v.velX = 0;
            } else if (left) {
                v.velX = -speed;
            } else if (right) {
                v.velX = speed;
            } else if (!left && !right) {
                v.velX = 0;
            }





        }

    }
}
