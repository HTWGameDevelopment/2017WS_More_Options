package com.moreoptions.prototype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;

/**
 * A screen that handles loading of assets as well as displaying progress.
 * TODO: Preload Screens, register Items, Sounds and Enemies in Database
 */
public class LoadingScreen implements Screen {

    private Stage stage;
    private Skin skin;

    private Label progressText;
    private ProgressBar progressBar;

    private AssetLoader assetLoader = AssetLoader.getInstance();
    private boolean doneLoading = false;

    private MoreOptions moreOptions;


    public LoadingScreen(Skin skin, MoreOptions moreOptions) {
        this.skin = skin;
        this.moreOptions = moreOptions;
        stage = new Stage();

        setupStage();
    }

    private void setupStage() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        progressText = new Label("Loading!", skin);
        progressText.getStyle().fontColor = Color.WHITE;
        progressBar = new ProgressBar(0,100,1,false, skin);
        mainTable.add(progressText);
        mainTable.row();
        mainTable.add(progressBar);

        stage.addActor(mainTable);
    }

    @Override
    public void show() {

        assetLoader.loadAll();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        checkProgress();

        stage.act();
        stage.draw();
    }

    private void checkProgress() {

        if(!doneLoading) {
            if (!assetLoader.update()) {
                progressBar.setValue(assetLoader.getAssetManager().getProgress() * 100);
            } else {
                doneLoading = true;
                moreOptions.startGame();
            }
        }
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
}
