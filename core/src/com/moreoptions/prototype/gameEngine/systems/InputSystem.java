package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.moreoptions.prototype.gameEngine.components.PlayerComponent;
import com.moreoptions.prototype.gameEngine.components.VelocityComponent;

/**
 * Created by Dennis on 23.10.2017.
 */

public class InputSystem extends EntitySystem {

    private static InputSystem instance = new InputSystem();

    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;

    public static InputSystem getInstance() {
        if(instance == null) {
            instance = new InputSystem();
        }
        return instance;
    }

    private InputSystem() {

    }

    Family family = Family.all(PlayerComponent.class).get();

    @Override
    public void update(float deltaTime) {

        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(family);

        for(Entity e : entities) {

            VelocityComponent v = e.getComponent(VelocityComponent.class);

            if (up && down) {
                v.setVelY(0);
            } else if (up) {
                v.setVelY(v.getSpeed());
            } else if (down) {
                v.setVelY(-v.getSpeed());
            } else if (!up && !down) {
                v.setVelY(0);
            }

            if (left && right) {
                v.setVelX(0);
            } else if (left) {
                v.setVelX(-v.getSpeed());
            } else if (right) {
                v.setVelX(v.getSpeed());
            } else if (!left && !right) {
                v.setVelX(0);
            }
        }
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
