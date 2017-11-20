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
import com.moreoptions.prototype.gameEngine.data.Player;

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
            updateVelocity(e);
        }
    }

    private void updateVelocity(Entity e) {
        Player p = e.getComponent(PlayerComponent.class).getPlayer();
        VelocityComponent v = e.getComponent(VelocityComponent.class);

        InputState playerInput = p.getInputState();

        if (playerInput.isMoveUp() && playerInput.isMoveDown()) {
            v.setVelY(v.getVelY() * v.getDeceleration());

        } else if (playerInput.isMoveUp()) {
            v.setVelY(v.getSpeed());
        } else if (playerInput.isMoveDown()) {
            v.setVelY(-v.getSpeed());
        } else if (!playerInput.isMoveUp() && !playerInput.isMoveDown()) {
            v.setVelY(v.getVelY() * v.getDeceleration()); //TODO move this to own system?
        }

        if (playerInput.isMoveLeft() && playerInput.isMoveRight()) {
            v.setVelX(v.getVelX() * v.getDeceleration());
        } else if (playerInput.isMoveLeft()) {
            v.setVelX(-v.getSpeed());
        } else if (playerInput.isMoveRight()) {
            v.setVelX(v.getSpeed());
        } else if (!playerInput.isMoveLeft() && !playerInput.isMoveRight()) {
            v.setVelX(v.getVelX() * v.getDeceleration());
        }
    }
}
