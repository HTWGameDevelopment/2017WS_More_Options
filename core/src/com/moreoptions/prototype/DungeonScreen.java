package com.moreoptions.prototype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.SoundDatabase;
import com.moreoptions.prototype.gameEngine.util.EventFactory;
import com.moreoptions.prototype.userInterface.UserInterface;

/**
 * Created by denwe on 12.11.2017.
 */
public class DungeonScreen implements Screen {

    GameWorld engine;
    MoreOptions moreOptions;
    Stage pauseScreen;
    private boolean paused = false;


    public DungeonScreen(MoreOptions moreOptions) {

        setupPauseScreen();
        engine = GameWorld.getInstance();
        this.moreOptions = moreOptions;
    }

    private void setupPauseScreen() {
        pauseScreen = new Stage();

        Skin skin = new Skin(Gdx.files.internal("comic/skin/comic-ui.json"));

        Table pauseTable = new Table();
        TextButton backButton = new TextButton("Back to Main Menu", skin);
        TextButton continueButton = new TextButton("Continue", skin);

        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                paused = false;
                SoundDatabase.getInstance().unpauseMusic();
                engine.updateInput();
                super.clicked(event, x, y);
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO save

                engine.updateInput();
                SoundDatabase.getInstance().pauseMusic();

                EventFactory.gameOver();
                paused = false;
                super.clicked(event, x, y);
            }
        });

        pauseTable.add(continueButton);
        pauseTable.row();
        pauseTable.add(backButton);

        pauseTable.setFillParent(true);

        pauseScreen.addActor(pauseTable);




    }

    @Override
    public void show() {
        engine.updateInput();

        engine.generateNewLevel();

        SoundDatabase.getInstance().playMusic(Consts.Sound.DUNGEON_MUSIC);
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            paused = !paused;
            SoundDatabase.getInstance().pauseMusic();
            if(paused) {

                pauseScreen.getViewport().setScreenSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                Gdx.input.setInputProcessor(pauseScreen);
            }   else engine.updateInput();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(paused) {
            pauseScreen.act();
            pauseScreen.draw();
        } else engine.update(delta);
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

    public void restart() {
    }
}
