package com.moreoptions.prototype.gameEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.moreoptions.prototype.MoreOptions;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Hotkey;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by denwe on 28.01.2018.
 */
public class HotkeyScreen implements Screen {

    private MoreOptions moreOptions;
    private Skin skin;
    private Stage stage;

    private ScrollPane scrollPane;

    private TextField focussed;

    private Table mainTable;

    private ArrayList<Pair<Label,TextField>> hotkeyList = new ArrayList<Pair<Label, TextField>>();

    public HotkeyScreen(MoreOptions moreOptions) {
        this.moreOptions = moreOptions;
        this.skin = AssetLoader.getInstance().getSkin();

        stage = new Stage();
        mainTable = new Table();
        mainTable.setFillParent(true);
        scrollPane = new ScrollPane(mainTable, skin);
        scrollPane.setFillParent(true);
        stage.addActor(scrollPane);
        setupHotkeys();


    }


    public void refreshHotkeys() {
        HashMap<Integer, Hotkey.GameHotkey> p = GameState.getInstance().getGameProfile().getGameHotkeys();
    }

    public void setupHotkeys() {

        HashMap<Integer, Hotkey.GameHotkey> p = GameState.getInstance().getGameProfile().getGameHotkeys();




        for(Map.Entry<Integer, Hotkey.GameHotkey> entry : p.entrySet()) {

            Label label = new Label(entry.getValue().toString(), skin);
            final TextField field = new TextField(Input.Keys.toString(entry.getKey()), skin);

            field.setTextFieldListener(new TextField.TextFieldListener() {
                @Override
                public void keyTyped(TextField textField, char c) {
                    if(field == focussed) {
                        textField.setText(textField.getText());
                    }
                }
            });


            field.addListener(new ClickListener() {

                @Override
                public boolean keyDown(InputEvent event, int keycode) {

                    if(field == focussed) {
                        field.setText(Input.Keys.toString(keycode));
                    }

                    return false;
                }

                @Override
                public boolean keyTyped(InputEvent event, char character) {
                    if(field.getText().length() <=2) field.setText(field.getText().substring(0,1));
                    return true;
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    focussed = field;
                    super.clicked(event, x, y);
                }
            });





            hotkeyList.add(new Pair<Label, TextField>(label,field));

        }

        for(Pair<Label, TextField> entry : hotkeyList) {

            mainTable.add(entry.getKey());
            mainTable.add(entry.getValue());
            mainTable.row();


        }

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
        stage.getViewport().update(width, height);
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
