package com.moreoptions.prototype;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.moreoptions.prototype.gameEngine.GameEngine;
import com.moreoptions.prototype.gameEngine.components.DebugColorComponent;
import com.moreoptions.prototype.gameEngine.components.PlayerComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.VelocityComponent;
import com.moreoptions.prototype.gameEngine.data.Profile;
import com.moreoptions.prototype.gameEngine.input.GameInputProcessor;
import com.moreoptions.prototype.gameEngine.systems.DebugRenderSystem;
import com.moreoptions.prototype.gameEngine.systems.MovementSystem;
import com.moreoptions.prototype.gameEngine.systems.InputSystem;

public class MoreOptions extends ApplicationAdapter {

	private GameEngine engine;
	private FPSLogger logger;
	
	@Override
	public void create () {
        engine = GameEngine.getInstance();
        logger = new FPSLogger();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(Gdx.graphics.getDeltaTime());
        logger.log();

	}
	
	@Override
	public void dispose () {

	}

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        engine.resize(width,height);
    }
}
