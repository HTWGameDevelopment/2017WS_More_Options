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
import com.moreoptions.prototype.gameEngine.data.Strings;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;
import com.moreoptions.prototype.gameEngine.util.EventFactory;
import com.moreoptions.prototype.userInterface.UserInterface;

/**
 * Screen where World + GameUI is displayed
 */
public class DungeonScreen implements Screen {

    private GameWorld engine;
    private MoreOptions moreOptions;
    private Stage pauseScreen;
    private boolean paused = false;

    public DungeonScreen(MoreOptions moreOptions) {
        setupPauseScreen();
        engine = GameWorld.getInstance();
        this.moreOptions = moreOptions;
    }

    private void setupPauseScreen() {
        pauseScreen = new Stage();

        Skin skin = AssetLoader.getInstance().getSkin();

        Table pauseTable = new Table();
        TextButton backButton = new TextButton(Strings.Menu.DS_BACK_TO_MAIN_MENU, skin);
        TextButton continueButton = new TextButton(Strings.Menu.DS_CONTINUE, skin);

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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            paused = !paused;
            SoundDatabase.getInstance().pauseMusic();
            if(paused) {

                pauseScreen.getViewport().setScreenSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                Gdx.input.setInputProcessor(pauseScreen);
            }   else engine.updateInput();
        }

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
