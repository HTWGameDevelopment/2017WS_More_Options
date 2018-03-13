package com.moreoptions.prototype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;

/**
 * Created by Dennis on 23.02.2018.
 */
public class FirstStartupScreen implements Screen {

    private Stage ui = new Stage();
    private Skin skin;

    //UI Elements
    private Label label;
    private TextButton onlineFeaturesButton;
    private TextButton denyOnlineFeaturesButton;

    private MoreOptions moreOptions;


    public FirstStartupScreen(MoreOptions moreOptions) {

        this.moreOptions = moreOptions;
        skin = AssetLoader.getInstance().getSkin();

        setupText();
        setupButtons();
        setupUI();
    }

    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.add(label).center().fill();
        table.row();
        table.add(onlineFeaturesButton);
        table.add(denyOnlineFeaturesButton);
        ui.addActor(table);

    }

    private void setupText() {
        label = new Label("Use cloud features?", skin);
    }

    private void setupButtons() {
        denyOnlineFeaturesButton = new TextButton("No" ,skin);
        denyOnlineFeaturesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameState.getInstance().createNewProfile();
                GameState.getInstance().disableOnlineFeatures();
                moreOptions.showMenuScreen();
                super.clicked(event, x, y);
            }
        });

        onlineFeaturesButton = new TextButton("Yes", skin);
        onlineFeaturesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameState.getInstance().createNewProfile();
                moreOptions.showLoginScreen();
                super.clicked(event, x, y);
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(ui);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ui.act();
        ui.draw();
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
