package com.moreoptions.prototype.userInterface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.moreoptions.prototype.gameEngine.data.Player;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;

/**
 * Created by denwe on 08.01.2018.
 */
public class UserInterface {

    private Stage stage;

    private ProgressBar playerHealthBar;
    private Image goldImage;
    private Label goldCounter;

    Player p;

    Skin skin;


    public UserInterface(Viewport v, SpriteBatch b, Player p) {

        this.p = p;
        skin = AssetLoader.getInstance().getSkin();
        stage = new Stage(v, b);

        stage.setDebugAll(true);//Init Stage

        Table t = new Table();
        t.setFillParent(true);

        playerHealthBar = new ProgressBar(0, 3, 1, false, skin);
        playerHealthBar.setValue(playerHealthBar.getMaxValue());
        playerHealthBar.getStyle().background.setMinHeight(10);
        playerHealthBar.getStyle().knobBefore.setMinHeight(10);

        playerHealthBar.setHeight(10);


        goldImage = new Image();
        goldCounter = new Label("", skin);
        goldCounter.getStyle().fontColor = Color.WHITE;
        goldCounter.setColor(Color.WHITE);
        t.add(playerHealthBar).height(10).pad(5);
        t.row();
        t.add(goldCounter).left();
        t.align(Align.left).align(Align.topLeft);


        stage.addActor(t);



    }

    public void draw() {
       stage.act();
       stage.draw();
    }


    public void update() {

        playerHealthBar.setRange(0, p.getStats().getMaxHealth());
        playerHealthBar.setValue(p.getStats().getCurrentHealth());
        goldCounter.setText("Gold:" + String.valueOf(p.getStats().getMoney()));
    }
}
