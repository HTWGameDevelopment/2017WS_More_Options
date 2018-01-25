package com.moreoptions.prototype;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventListener;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventSubscriber;

public class MoreOptions extends Game {


	private StartGameScreen screen;
	private DungeonScreen dungeonScreen;
	private LoginScreen loginScreen;
	private GameOverScreen gameOverScreen;

	EventSubscriber subscriber;

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


		AssetLoader.getInstance().loadAll();

		while(!AssetLoader.getInstance().update()) {

		}
		screen = new StartGameScreen(this);
		dungeonScreen = new DungeonScreen(this);
		loginScreen = new LoginScreen(this);
		gameOverScreen = new GameOverScreen(this);
		setScreen(screen);
	}

	public void showDungeon() {
		dungeonScreen.restart();
		setScreen(dungeonScreen);
	}

	public void showStartScreen() {

		setScreen(screen);
	}

	public StartGameScreen getMainMenu() {
		return screen;
	}

	public void showLoginScreen() {
		setScreen(loginScreen);
		System.out.println("Showing loginscreen");
	}
}
