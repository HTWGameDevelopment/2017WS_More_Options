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

/**
 * Created by Dennis on 25.01.2018.
 */
public class GameOverScreen implements Screen {

    private Stage stage;
    private Table mainTable;

    private MoreOptions moreOptions;

    public GameOverScreen(final MoreOptions moreOptions) {
        stage = new Stage();

        this.moreOptions = moreOptions;
        Skin skin = new Skin(Gdx.files.internal("comic/skin/comic-ui.json"));

        mainTable = new Table();
        Label gameOverText = new Label("You died! Better luck next time!", skin);
        gameOverText.getStyle().fontColor = Color.WHITE;
        TextButton backToMainMenu = new TextButton("Back to Main Menu", skin);
        backToMainMenu.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                moreOptions.showStartScreen();
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
