package com.moreoptions.prototype;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;

public class MoreOptions extends Game {

	private GameWorld engine;
	private FPSLogger logger;
	private StartGameScreen screen;
	private DungeonScreen dungeonScreen;

	
	public MoreOptions() {

	}

	public void create() {


		AssetLoader.getInstance().loadAll();

		while(!AssetLoader.getInstance().update()) {

		}
		screen = new StartGameScreen(this);
		dungeonScreen = new DungeonScreen(this);
		setScreen(dungeonScreen);
	}

	public void showDungeon() {
		setScreen(dungeonScreen);
	}

	public void showStartScreen() {
		setScreen(screen);
	}

}
