package com.moreoptions.prototype;

import com.badlogic.gdx.Game;
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


	EventSubscriber subscriber;
	
	public MoreOptions() {

		subscriber = new EventSubscriber();
		subscriber.subscribe(Consts.GAME_OVER, new EventListener() {
			@Override
			public boolean trigger(Event e) {
				showStartScreen();
				return false;
			}
		});

	}

	public void create() {


		AssetLoader.getInstance().loadAll();

		while(!AssetLoader.getInstance().update()) {

		}
		screen = new StartGameScreen(this);
		dungeonScreen = new DungeonScreen(this);
		setScreen(screen);
	}

	public void showDungeon() {
		setScreen(dungeonScreen);
	}

	public void showStartScreen() {
		setScreen(screen);
	}

}
