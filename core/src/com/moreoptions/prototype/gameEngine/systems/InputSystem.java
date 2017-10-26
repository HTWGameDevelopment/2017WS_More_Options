package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Game;
import com.moreoptions.prototype.gameEngine.components.PlayerComponent;
import com.moreoptions.prototype.gameEngine.components.VelocityComponent;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.InputState;

/**
 * System that manipulates Entities based on Player Input
 */

public class InputSystem extends EntitySystem {

    private static InputSystem instance = new InputSystem();

    public static InputSystem getInstance() {
        if(instance == null) {
            instance = new InputSystem();
        }
        return instance;
    }

    private InputSystem() {

    }

    private Family family = Family.all(PlayerComponent.class).get();

    @Override
    public void update(float deltaTime) {

        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(family);

        for(Entity e : entities) {
            if(e.getComponent(PlayerComponent.class).getId() == 1) {
                VelocityComponent v = e.getComponent(VelocityComponent.class);
                InputState p1 = GameState.getInstance().getPlayerOne().getInputState();

                if (p1.isMoveUp() && p1.isMoveDown()) {
                    v.setVelY(0);
                } else if (p1.isMoveUp()) {
                    v.setVelY(v.getSpeed());
                } else if (p1.isMoveDown()) {
                    v.setVelY(-v.getSpeed());
                } else if (!p1.isMoveUp() && !p1.isMoveDown()) {
                    v.setVelY(0);
                }

                if (p1.isMoveLeft() && p1.isMoveRight()) {
                    v.setVelX(0);
                } else if (p1.isMoveLeft()) {
                    v.setVelX(-v.getSpeed());
                } else if (p1.isMoveRight()) {
                    v.setVelX(v.getSpeed());
                } else if (!p1.isMoveLeft() && !p1.isMoveRight()) {
                    v.setVelX(0);
                }
            }
        }
    }
}
