package com.moreoptions.prototype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.moreoptions.prototype.gameEngine.data.GameOptions;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;
import com.moreoptions.prototype.gameEngine.util.dataCollector.ApiRequest;
import com.moreoptions.prototype.gameEngine.util.dataCollector.LoginDetails;

import java.util.ArrayList;
import java.util.Stack;

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

        if(!doneLoading) checkProgress();

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
                checkOnlineFeatures();
            }
        }
    }

    private void checkOnlineFeatures() {
        //First, check if local game exists
        if(GameState.getInstance().doesLocalGameStateExist()) {
            //If yes, load Game State for later comparision
            GameState.getInstance().loadLocalGameState();
            //Check if online-features are enabled
            if(GameOptions.isOnlineEnabled()) {
                //Check if data is correct
                if(GameOptions.hasLoginDetails()) {
                    LoginDetails details = GameState.getInstance().getGameProfile().getLoginDetails();
                    progressText.setText("Logging in!");
                    ApiRequest.login(details, new LoginResponseListener());
                } else {
                    moreOptions.showLoginScreen();
                }
            } else {
                moreOptions.showMenuScreen();
            }
        } else {
            moreOptions.showFirstScreen();
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

    class LoginResponseListener implements Net.HttpResponseListener {

        @Override
        public void handleHttpResponse(Net.HttpResponse httpResponse) {
            if(httpResponse.getStatus().getStatusCode() == 200) {
                updateSaveGame();
            }
        }

        @Override
        public void failed(Throwable t) {

        }

        @Override
        public void cancelled() {

        }
    }

    private void updateSaveGame() {
        ApiRequest.getLatestSaveGame(null, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
                    GameState.getInstance().loadCloudProfile(httpResponse.getResultAsString());
                    moreOptions.showMenuScreen();
                } else if(httpResponse.getStatus().getStatusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE){
                    System.out.println("error");
                } else {
                    System.out.println("error");
                }
            }

            @Override
            public void failed(Throwable t) {
                System.out.println("error11");
            }

            @Override
            public void cancelled() {
                System.out.println("error");
            }
        });
    }

}
