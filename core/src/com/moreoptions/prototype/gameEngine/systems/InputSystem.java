package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.PlayerComponent;
import com.moreoptions.prototype.gameEngine.components.StatsComponent;
import com.moreoptions.prototype.gameEngine.components.VelocityComponent;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.InputState;
import com.moreoptions.prototype.gameEngine.data.Player;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventBus;

/**
 * System that manipulates Entities based on Player Input
 */

public class InputSystem extends EntitySystem {

    private ComponentMapper<PlayerComponent> pcMapper = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<VelocityComponent> vcMapper = ComponentMapper.getFor(VelocityComponent.class);

    private static InputSystem instance = new InputSystem();
    private ComponentMapper<StatsComponent> statsMapper = ComponentMapper.getFor(StatsComponent.class);

    public static InputSystem getInstance() {
        if (instance == null) {
            instance = new InputSystem();
        }
        return instance;
    }

    private InputSystem() {

    }

    private Family family = Family.one(PlayerComponent.class).get();

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(family);

        for (Entity e : entities) {
            updateVelocity(e);
            updateShots(e);
        }
    }

    private void updateVelocity(Entity e) {

        PlayerComponent pc = pcMapper.get(e);
        Player p = pc.getPlayer();
        VelocityComponent v = vcMapper.get(e);
        Statistics stats = statsMapper.get(e).getStats();

        InputState playerInput = p.getInputState();

        if (playerInput.isMoveUp() && playerInput.isMoveDown()) {
            v.setVelY(v.getVelY() * v.getDeceleration());

        } else if (playerInput.isMoveUp()) {
            v.setVelY(stats.getSpeed());
        } else if (playerInput.isMoveDown()) {
            v.setVelY(-stats.getSpeed());
        } else if (!playerInput.isMoveUp() && !playerInput.isMoveDown()) {
            v.setVelY(v.getVelY() * v.getDeceleration()); //TODO move this to own system?
        }

        if (playerInput.isMoveLeft() && playerInput.isMoveRight()) {
            v.setVelX(v.getVelX() * v.getDeceleration());
        } else if (playerInput.isMoveLeft()) {
            v.setVelX(-stats.getSpeed());
        } else if (playerInput.isMoveRight()) {
            v.setVelX(stats.getSpeed());
        } else if (!playerInput.isMoveLeft() && !playerInput.isMoveRight()) {
            v.setVelX(v.getVelX() * v.getDeceleration());
        }

    }

    public void updateShots(Entity e) {

        PlayerComponent pc = pcMapper.get(e);
        Player p = pc.getPlayer();
        Statistics stats = statsMapper.get(e).getStats();
        InputState playerInput = p.getInputState();
        if (playerInput.isShootDown() || playerInput.isShootLeft() || playerInput.isShootRight() || playerInput.isShootUp()) {
            if (stats.getCurrentShotCooldown() >= stats.getFireRate()) {
                Event event = new Event(Consts.SHOOT_EVENT);
                event.addData(Consts.ENTITY, e);

                if (playerInput.isShootDown()) {
                    event.addData(Consts.DIRECTION, new Vector2(0, -1));
                    EventBus.getInstance().addEvent(event);
                } else if (playerInput.isShootUp()) {
                    event.addData(Consts.DIRECTION, new Vector2(0, 1));
                    EventBus.getInstance().addEvent(event);
                } else if (playerInput.isShootLeft()) {
                    event.addData(Consts.DIRECTION, new Vector2(-1, 0));
                    EventBus.getInstance().addEvent(event);
                } else if (playerInput.isShootRight()) {
                    event.addData(Consts.DIRECTION, new Vector2(1, 0));
                    EventBus.getInstance().addEvent(event);
                }
                stats.setCurrentShotCooldown(0);
            }
        }
    }
}