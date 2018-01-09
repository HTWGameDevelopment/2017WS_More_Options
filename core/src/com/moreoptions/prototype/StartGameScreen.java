package com.moreoptions.prototype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by denwe on 10.11.2017.
 */
public class StartGameScreen implements Screen {

    private Stage stage;
    private MoreOptions moreOptions;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private FitViewport viewport;


    public StartGameScreen(final MoreOptions moreOptions) {

        batch = new SpriteBatch();


        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        stage = new Stage(viewport, batch);

        Skin skin = new Skin(Gdx.files.internal("comic/skin/comic-ui.json"));

        stage = new Stage();
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);



        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true,true);

        TextButton newGameButton = new TextButton("New Game",skin);

        newGameButton.addListener(new ChangeListener() {
                                      @Override
                                      public void changed(ChangeEvent event, Actor actor) {
                                        moreOptions.showDungeon();
                                      }
                                  }
        );

        TextButton continueButton = new TextButton("More Options", skin);
        TextButton exitGameButton = new TextButton("Exit",skin);

        table.add(newGameButton);
        table.row();
        table.add(continueButton);
        table.row();
        table.add(exitGameButton);
        stage.addActor(table);


    }


    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
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
        //Do everything that needs to be done to start the game.
    }
}
