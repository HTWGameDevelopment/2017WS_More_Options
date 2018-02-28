package com.moreoptions.prototype;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.HotkeyScreen;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventListener;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventSubscriber;

public class MoreOptions extends Game {


	private StartGameScreen screen;
	private DungeonScreen dungeonScreen;
	private LoginScreen loginScreen;
	private GameOverScreen gameOverScreen;
	private LoadingScreen loadingScreen;
	private StatsScreen statsScreen;
	private FirstStartupScreen firstStartScreen;
	private EventSubscriber subscriber;

	public MoreOptions() {
		subscriber = new EventSubscriber();
		subscriber.subscribe(Consts.GAME_OVER, new EventListener() {
			@Override
			public boolean trigger(Event e) {
				showGameOverScreen();
				return false;
			}
		});
	}

	private void showGameOverScreen() {
		setScreen(gameOverScreen);
	}

	public void create() {
		//Preload skin!
		AssetLoader.getInstance().loadSkin();
		AssetLoader.getInstance().getAssetManager().finishLoading();
		loadingScreen = new LoadingScreen(AssetLoader.getInstance().getSkin(), this);

		//Everything prepared to show loadingscreen.
		setScreen(loadingScreen);
	}

	public void showDungeon() {
		dungeonScreen.restart();
		setScreen(dungeonScreen);
	}

	public void showStartScreen() {

		if(GameState.getInstance().doesLocalGameStateExist()) {
			GameState.getInstance().loadLocalGameState();
			setScreen(loginScreen);
		} else {
			setScreen(firstStartScreen);
		}
	}

	public void showStatsScreen(){
		setScreen(statsScreen);
	}

	public StartGameScreen getMainMenu() {
		return screen;
	}

	public void showLoginScreen() {
		setScreen(loginScreen);
	}

	public void startGame() {
		screen = new StartGameScreen(this);
		dungeonScreen = new DungeonScreen( this);
		loginScreen = new LoginScreen( this);
		gameOverScreen = new GameOverScreen( this);
		statsScreen = new StatsScreen(this);
		firstStartScreen = new FirstStartupScreen(this);
		showStartScreen();
	}

}
