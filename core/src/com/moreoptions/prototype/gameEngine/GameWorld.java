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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Player;
import com.moreoptions.prototype.gameEngine.data.ai.CSpace;
import com.moreoptions.prototype.gameEngine.data.ai.StandardCSpace;
import com.moreoptions.prototype.gameEngine.input.GameInputProcessor;
import com.moreoptions.prototype.gameEngine.systems.*;
import com.moreoptions.prototype.level.LevelManager;

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

    StandardCSpace cSpace;

    LevelManager levelManager;


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

        levelManager = new LevelManager(this);

        Entity debugMonsterEntity = new Entity();
        debugMonsterEntity.add(new PositionComponent(150, 100));
        debugMonsterEntity.add(new CollisionComponent());
        debugMonsterEntity.add(new CircleCollisionComponent(150f, 100f, 10));
        debugMonsterEntity.add(new DebugCircleComponent(new Vector2(150, 100), 10));
        debugMonsterEntity.add(new VelocityComponent(0f, 0f));
        debugMonsterEntity.add(new DebugColorComponent(Color.FIREBRICK));

        addEntity(debugMonsterEntity);

        cSpace = new StandardCSpace(levelManager.getCurrentRoom(), debugMonsterEntity);


    }

    @Override
    public void update(float deltaTime) {
        if(!loaded) {
            loaded = true;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            System.out.println(getEntities().size());
        }
        super.update(deltaTime);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        cSpace.debugDraw(renderer);
        renderer.end();

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
}
