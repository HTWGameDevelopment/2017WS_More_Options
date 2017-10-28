package com.moreoptions.prototype.gameEngine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moreoptions.prototype.gameEngine.components.DebugColorComponent;
import com.moreoptions.prototype.gameEngine.components.PlayerComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.VelocityComponent;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.input.GameInputProcessor;
import com.moreoptions.prototype.gameEngine.systems.DebugRenderSystem;
import com.moreoptions.prototype.gameEngine.systems.InputSystem;
import com.moreoptions.prototype.gameEngine.systems.MovementSystem;

/**
 *
 */
public class GameEngine extends Engine {

    private static GameEngine gameEngine;

    public static GameEngine getInstance() {
        if(gameEngine == null) gameEngine = new GameEngine();
        return gameEngine;
    }

    private GameEngine() {
        demoSetup();
    }

    private void demoSetup() {

        Gdx.input.setInputProcessor(new GameInputProcessor());

        Entity playerEntity = new Entity();
        playerEntity.add(new PlayerComponent(GameState.getInstance().getPlayerOne()));
        playerEntity.add(new PositionComponent(50,50));
        playerEntity.add(new VelocityComponent(350f,0.75f));
        playerEntity.add(new DebugColorComponent(new Color(76f/255f, 176/255f, 186f/255f,1)));

        addEntity(playerEntity);

        addSystem(InputSystem.getInstance());
        addSystem(new MovementSystem());
        addSystem(new DebugRenderSystem());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
