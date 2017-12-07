package com.moreoptions.prototype.gameEngine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Player;
import com.moreoptions.prototype.gameEngine.input.GameInputProcessor;
import com.moreoptions.prototype.gameEngine.systems.*;
import com.moreoptions.prototype.level.LevelManager;

/**
 *
 */
public class GameWorld extends Engine {

    private static GameWorld gameEngine;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport fv;
    private LevelManager levelManager;
    private GameInputProcessor processor;

    private GameWorld() {
        demoSetup();
    }

    public void demoSetup() {

        camera = new OrthographicCamera(640,640);
        camera.position.set(17*32/2,11*32/2,0);

        fv = new FitViewport(17*32 , 11*32, camera);
        camera.update();
        fv.update(Consts.GAME_WIDTH,Consts.GAME_HEIGHT);
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        Gdx.graphics.setWindowedMode(Consts.GAME_WIDTH*2,Consts.GAME_HEIGHT*2);

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
        addSystem(new FontRenderSystem(batch));
        addSystem(new TimedSystem());
        addSystem(new PickupSystem());
        addSystem(new ProjectileSystem());
        addSystem(new AISystem(renderer));
        addSystem(new PlayerSystem());
        addSystem(new EnemySystem());
        levelManager = new LevelManager(this);

    }

    @Override
    public void update(float deltaTime) {
        levelManager.getCurrentRoom().getNavGraph().draw(renderer);
        super.update(deltaTime);
    }

    public void updateInput() {
        Gdx.input.setInputProcessor(processor);
    }

    public LevelManager getRoomManager() {
        return levelManager;
    }

    public static GameWorld getInstance() {
        if(gameEngine == null) gameEngine = new GameWorld();
        return gameEngine;
    }


}
