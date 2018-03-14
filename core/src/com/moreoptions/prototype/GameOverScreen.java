package com.moreoptions.prototype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.moreoptions.prototype.gameEngine.data.SoundDatabase;
import com.moreoptions.prototype.gameEngine.data.Strings;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;
import com.moreoptions.prototype.gameEngine.util.EventFactory;

/**
 * Screen that is displayed after the player dies.
 */
public class GameOverScreen implements Screen {

    private Stage stage;
    private Table mainTable;

    private MoreOptions moreOptions;

    public GameOverScreen(final MoreOptions moreOptions) {
        stage = new Stage();

        this.moreOptions = moreOptions;
        Skin skin = AssetLoader.getInstance().getSkin();

        mainTable = new Table();
        Label gameOverText = new Label(Strings.Menu.GO_YOU_DIED, skin);
        gameOverText.getStyle().fontColor = Color.WHITE;
        TextButton backToMainMenu = new TextButton(Strings.Menu.DS_BACK_TO_MAIN_MENU, skin);
        backToMainMenu.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                moreOptions.showMenuScreen();
                super.clicked(event, x, y);
            }

        });

        mainTable.setFillParent(true);
        mainTable.add(gameOverText);
        mainTable.row();
        mainTable.add(backToMainMenu).pad(50);

        stage.addActor(mainTable);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        SoundDatabase.getInstance().pauseMusic();


        EventFactory.saveGame();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().setScreenSize(width, height);
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
