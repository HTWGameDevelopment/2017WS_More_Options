package com.moreoptions.prototype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.google.gson.Gson;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatsScreen implements Screen {

    private Stage stage;

    private ArrayList<String> labelList = new ArrayList<String>();
    private HashMap<String, Float> ownData = new HashMap<String, Float>();
    private Table wrapTable = new Table();
    private Table statsTable = new Table();
    private Table statsTable2 = new Table();
    private Table utilTable = new Table();
    private Skin skin;
    private Label errorMessage;

    private MoreOptions moreOptions;

    public StatsScreen(MoreOptions moreOptions){
        skin = AssetLoader.getInstance().getSkin();
        stage = new Stage();
        this.moreOptions = moreOptions;


    }



    @Override
    public void show() {
        setupTable();
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            moreOptions.showStartScreen();
        }
    }

    @Override
    public void resize(int width, int height) { stage.getViewport().setScreenSize(width, height);}

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

    private void setupTable() {
        statsTable.clear();
        statsTable.setFillParent(true);
        HashMap<String,Float> hm = GameState.getInstance().getGameProfile().getStats();

        Label players = new Label("Player:",skin);

        Label stats = new Label("Stats:",skin);

        statsTable.add(players);
        statsTable.add(stats);
        statsTable.row();



        for(Map.Entry<String,Float> e : hm.entrySet()){
            //if(statsTable.getRows() != 0) statsTable.row();
            statsTable.row();
            String name = e.getKey();
            float value = e.getValue();
            Label lName = new Label(name,skin);
            Label lValue = new Label(String.valueOf(value),skin);

            labelList.add(name);

            ownData.put(name, value);

            statsTable.add(lName);
            statsTable.add(lValue);
        }

        TextButton backButton = new TextButton("Back", skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                moreOptions.showStartScreen();
                super.clicked(event, x, y);
            }
        });

        final TextField searchField = new TextField("",skin);

        TextButton searchButton = new TextButton("Search", skin);

        searchButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setupCompareTable(ownData);
                super.clicked(event, x, y);
            }
        });

        errorMessage = new Label("",skin);
        errorMessage.getStyle().fontColor = Color.WHITE;
        statsTable.row();
        statsTable.add(backButton);
        statsTable.row();
        statsTable.add(searchField);
        statsTable.row();
        statsTable.add(searchButton);
        statsTable.add(errorMessage).padTop(50).align(Align.center);
        stage.addActor(statsTable);
    }

    private void compare(String email) {

    }

    private void setupCompareTable(HashMap<String, Float> theirData) {
        if(!ownData.isEmpty()) {

            statsTable.clear();
            statsTable.row();
            Label players = new Label("Players:",skin);

            Label ownStats = new Label("Own stats:",skin);

            Label theirStats = new Label("Their stats:",skin);
            statsTable.add(players);
            statsTable.add(ownStats);
            statsTable.add(theirStats);
            statsTable.row();
            statsTable.debug();

            for(Map.Entry<String, Float> e : ownData.entrySet()) {

                float valueTheir = theirData.get(e.getKey());
                float ourData = e.getValue();
                String ourName = e.getKey();
                statsTable.add(new Label(ourName, skin));
                statsTable.add(new Label(String.valueOf(ourData), skin));
                statsTable.add(new Label(String.valueOf(valueTheir), skin));
                statsTable.row();
            }

            TextButton backButton = new TextButton("Back", skin);

            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    statsTable.clear();
                    moreOptions.showStatsScreen();
                    super.clicked(event, x, y);
                }
            });

            statsTable.add(backButton);

        }
    }

}
