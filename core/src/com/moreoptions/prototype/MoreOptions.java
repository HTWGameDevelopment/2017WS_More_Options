package com.moreoptions.prototype;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.DebugColorComponent;
import com.moreoptions.prototype.gameEngine.components.PlayerComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.VelocityComponent;
import com.moreoptions.prototype.gameEngine.data.Profile;
import com.moreoptions.prototype.gameEngine.input.GameInputProcessor;
import com.moreoptions.prototype.gameEngine.systems.DebugRenderSystem;
import com.moreoptions.prototype.gameEngine.systems.MovementSystem;
import com.moreoptions.prototype.gameEngine.systems.InputSystem;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;
import com.moreoptions.prototype.gameEngine.util.MapParser;

public class MoreOptions extends Game {

	private GameWorld engine;
	private FPSLogger logger;
	private StartGameScreen screen;
	private DungeonScreen dungeonScreen;

	
	public MoreOptions() {




	}

	public void create() {


		screen = new StartGameScreen(this);
		dungeonScreen = new DungeonScreen(this);
		AssetLoader.getInstance().loadRooms();

		while(!AssetLoader.getInstance().update()) {
			System.out.println("Updating");
		}
		setScreen(dungeonScreen);
	}

	public void showDungeon() {
		setScreen(dungeonScreen);
	}

	public void showStartScreen() {
		setScreen(screen);
	}

}
