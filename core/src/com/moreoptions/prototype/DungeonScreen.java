package com.moreoptions.prototype;

import com.badlogic.gdx.Screen;
import com.moreoptions.prototype.gameEngine.GameEngine;

/**
 * Created by denwe on 12.11.2017.
 */
public class DungeonScreen implements Screen {

    GameEngine engine;

    public DungeonScreen(MoreOptions moreOptions) {
        engine = GameEngine.getInstance();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
