package com.moreoptions.prototype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.google.gson.Gson;
import com.moreoptions.prototype.gameEngine.data.*;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;
import com.moreoptions.prototype.gameEngine.util.dataCollector.ApiRequest;

import java.util.HashMap;

/**
 * Created by denwe on 10.11.2017.
 */
public class StartGameScreen implements Screen {

    private Stage stage;
    private MoreOptions moreOptions;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private FitViewport viewport;


    private Image logo;

    private Skin skin;


    Label errorMessage;
    private Table table;


    public StartGameScreen(final MoreOptions moreOptions) {

        this.moreOptions = moreOptions;
        skin = AssetLoader.getInstance().getSkin();
        logo = new Image(new Texture(Gdx.files.internal("images/more_options.png")));
        stage = new Stage();
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(true, true);

        TextButton newGameButton = new TextButton(Strings.Menu.NEW_GAME, skin);

        newGameButton.addListener(new ChangeListener() {
                                      @Override
                                      public void changed(ChangeEvent event, Actor actor) {
                                          startGame();
                                      }
                                  }
        );

        TextButton exitGameButton = new TextButton(Strings.Menu.EXIT_GAME, skin);

        exitGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                super.clicked(event, x, y);
            }
        });

        TextButton statsButton = new TextButton("Stats", skin);

        statsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                moreOptions.showStatsScreen();
                super.clicked(event, x, y);
            }
        });


        table.add(logo);
        table.row();
        table.add(newGameButton);
        table.row();
        table.add(statsButton);
        table.row();
        table.add(exitGameButton);
        stage.addActor(table);

        errorMessage = new Label("",skin);
        //setupLoginDialog();

    }


    @Override
    public void show() {
        SoundDatabase.getInstance().playMusic("intro");
        Gdx.input.setInputProcessor(stage);
        System.out.println("Changed inputprocessor");
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

        stage.getViewport().update(width,height);

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

    public void startGame() {
        moreOptions.showDungeon();
    }

}
