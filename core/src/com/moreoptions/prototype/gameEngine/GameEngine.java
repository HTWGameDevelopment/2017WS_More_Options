package com.moreoptions.prototype.gameEngine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
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
import com.moreoptions.prototype.gameEngine.input.GameInputProcessor;
import com.moreoptions.prototype.gameEngine.systems.*;

/**
 *
 */
public class GameEngine extends Engine {

    private static GameEngine gameEngine;

    public static GameEngine getInstance() {
        if(gameEngine == null) gameEngine = new GameEngine();
        return gameEngine;
    }


    ShapeRenderer renderer;
    SpriteBatch batch;
    OrthographicCamera camera;
    FitViewport fv;
    BitmapFont f;

    private GameEngine() {

        demoSetup();
    }

    private void demoSetup() {

        int testw = 15 *32;
        int testh = 9 * 32;

        f = new BitmapFont();

        camera = new OrthographicCamera(640,640);
        camera.position.set(15*32/2,9*32/2,0);

        fv = new FitViewport(15*32 , 9*32, camera);
        camera.update();
        fv.update(testw,testh);
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        Gdx.graphics.setWindowedMode(testw,testh);


        renderer.setProjectionMatrix(camera.combined);

        GameInputProcessor processor;
        processor = new GameInputProcessor(camera);
        Gdx.input.setInputProcessor(processor);


        Room r = new Room();
        for(Entity e : r.getEntities()) {
            addEntity(e);
        }


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

        Entity pickup = new Entity();
        pickup.add(new PositionComponent(50,50));
        pickup.add(new DebugColorComponent(new Color(76f/255f, 176/255f, 186f/255f,1)));
        pickup.add(new CollisionComponent());
        pickup.add(new CircleCollisionComponent(50,50,10));
        pickup.add(new PickupComponent(new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {
                e.getComponent(PositionComponent.class).setX(100);
                e.getComponent(PositionComponent.class).setY(100);
                return true;
            }
        }));

        addEntity(pickup);

        Entity fontTest = new Entity();
        CombatTextComponent c = new CombatTextComponent();
        c.setText("Test");
        fontTest.add(c);
        fontTest.add(new PositionComponent(150,150));

        addEntity(fontTest);

        addSystem(InputSystem.getInstance());
        addSystem(new MovementSystem());
        addSystem(new DebugRenderSystem(renderer));
        addSystem(new FontRenderSystem(batch,f));
        addSystem(new TimedSystem());
        addSystem(new PickupSystem());
    }

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);

    }

    public void resize(int width, int height) {
        fv.update(width,height);
    }

    public float getTileSize() {
        return 32;
    }
}
