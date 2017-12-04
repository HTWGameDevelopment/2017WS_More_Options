package com.moreoptions.prototype.gameEngine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Player;
import com.moreoptions.prototype.gameEngine.data.ai.NavGraph;
import com.moreoptions.prototype.gameEngine.data.ai.Node;
import com.moreoptions.prototype.gameEngine.data.ai.StandardCSpace;
import com.moreoptions.prototype.gameEngine.input.GameInputProcessor;
import com.moreoptions.prototype.gameEngine.systems.*;
import com.moreoptions.prototype.level.LevelManager;

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
    FPSLogger fps = new FPSLogger();
    StandardCSpace cSpace;

    LevelManager levelManager;
    Entity debugMonsterEntity;
    ArrayList<Node> path;

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
        GameState.getInstance().addPlayer(p);



        addSystem(InputSystem.getInstance());
        addSystem(new MovementSystem());
        addSystem(new DoorCollisionSystem());
        addSystem(new TileRenderSystem(batch));
        addSystem(new DebugRenderSystem(renderer));
        addSystem(new FontRenderSystem(batch,f));
        addSystem(new TimedSystem());
        addSystem(new PickupSystem());
        addSystem(new ProjectileSystem());
        addSystem(new AISystem(renderer));
        levelManager = new LevelManager(this);






    }

    @Override
    public void update(float deltaTime) {
        if(!loaded) {
            loaded = true;
        }

        levelManager.getCurrentRoom().getNavGraph().draw(renderer);

        super.update(deltaTime);

        fps.log();

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

    public LevelManager getRoomManager() {
        return levelManager;
    }

    public ImmutableArray<Entity> getPlayerEntities() {
        Family f = Family.all(PlayerComponent.class).get();
        return getEntitiesFor(f);
    }
}
