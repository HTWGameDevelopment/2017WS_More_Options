package com.moreoptions.prototype.gameEngine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Player;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.callback.PickupEvent;
import com.moreoptions.prototype.gameEngine.exceptions.MissdefinedTileException;
import com.moreoptions.prototype.gameEngine.input.GameInputProcessor;
import com.moreoptions.prototype.gameEngine.systems.*;
import com.moreoptions.prototype.gameEngine.util.MapParser;
import com.moreoptions.prototype.level.Level;
import com.moreoptions.prototype.level.LevelBlueprint;
import com.moreoptions.prototype.level.LevelManager;
import com.moreoptions.prototype.level.StandardLevelGenerator;

import java.util.ArrayList;

/**
 *
 */
public class GameWorld extends Engine {

    private static GameWorld gameEngine;
    private boolean filled = true;
    private boolean loaded = false;

    public static GameWorld getInstance() {
        if(gameEngine == null) gameEngine = new GameWorld();
        return gameEngine;
    }


    ShapeRenderer renderer;
    SpriteBatch batch;
    OrthographicCamera camera;
    FitViewport fv;
    BitmapFont f;

    LevelManager manager;


    GameInputProcessor processor;

    private GameWorld() {
        demoSetup();
    }

    public void demoSetup() {

        int testw = 15 *32;
        int testh = 9 * 32;

        f = new BitmapFont();

        camera = new OrthographicCamera(640,640);
        camera.position.set(17*32/2,11*32/2,0);

        fv = new FitViewport(17*32 , 11*32, camera);
        camera.update();
        fv.update(testw,testh);
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        Gdx.graphics.setWindowedMode(testw,testh);


        renderer.setProjectionMatrix(camera.combined);

        processor = new GameInputProcessor(camera);
        Gdx.input.setInputProcessor(processor);





        Player p = new Player();
        processor.addPlayer(p);



        Entity playerEntity = new Entity();
        playerEntity.add(new PlayerComponent(p));
        playerEntity.add(new PositionComponent(100,100));
        playerEntity.add(new VelocityComponent(150f,0.75f));
        playerEntity.add(new DebugColorComponent(new Color(76f/255f, 176/255f, 186f/255f,1)));
        playerEntity.add(new CollisionComponent());
        playerEntity.add(new CircleCollisionComponent(100,100,10));

        addEntity(playerEntity);


        addSystem(InputSystem.getInstance());
        addSystem(new MovementSystem());

        addSystem(new TileRenderSystem(batch));
        addSystem(new DebugRenderSystem(renderer));
        addSystem(new FontRenderSystem(batch,f));
        addSystem(new TimedSystem());
        addSystem(new PickupSystem());


        ArrayList<Entity> e = null;
        try {
            e = MapParser.loadRooms();
        } catch (MissdefinedTileException e1) {
            e1.printStackTrace();
        }
        for(Entity ex : e) {
            //addEntity(ex);
        }

        System.out.println("None");

        manager = new LevelManager(this);
    }

    @Override
    public void update(float deltaTime) {
        if(!loaded) {
            manager.initStartingLevel();
            loaded = true;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            manager.test();
        }
        super.update(deltaTime);


    }

    public void resize(int width, int height) {
        fv.update(width,height);
    }

    public float getTileSize() {
        return 32;
    }

    public void updateInput() {

        Gdx.input.setInputProcessor(processor);
    }

}
