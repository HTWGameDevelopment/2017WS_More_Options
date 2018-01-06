package com.moreoptions.prototype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.moreoptions.prototype.gameEngine.GameWorld;

/**
 * Created by denwe on 12.11.2017.
 */
public class DungeonScreen implements Screen {

    GameWorld engine;
    MoreOptions moreOptions;

    public DungeonScreen(MoreOptions moreOptions) {
        engine = GameWorld.getInstance();
        this.moreOptions = moreOptions;
    }

    @Override
    public void show() {
        engine.updateInput();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
