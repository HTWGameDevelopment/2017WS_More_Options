package com.moreoptions.prototype.gameEngine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.input.GameInputProcessor;
import com.moreoptions.prototype.gameEngine.systems.DebugRenderSystem;
import com.moreoptions.prototype.gameEngine.systems.InputSystem;
import com.moreoptions.prototype.gameEngine.systems.MovementSystem;

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
    OrthographicCamera camera;
    FitViewport fv;

    private GameEngine() {

        demoSetup();
    }

    private void demoSetup() {

        int testw = 15 *32;
        int testh = 9 * 32;


        camera = new OrthographicCamera(640,640);
        camera.position.set(15*32/2,9*32/2,0);

        fv = new FitViewport(15*32 , 9*32, camera);
        camera.update();
        fv.update(testw,testh);
        renderer = new ShapeRenderer();
        Gdx.graphics.setWindowedMode(testw,testh);


        renderer.setProjectionMatrix(camera.combined);

        GameInputProcessor processor;
        processor = new GameInputProcessor(camera);
        Gdx.input.setInputProcessor(processor);


        Room r = new Room();
        for(Entity e : r.getEntities()) {
            addEntity(e);
        }

        Entity playerEntity = new Entity();
        playerEntity.add(new PlayerComponent(GameState.getInstance().getPlayerOne()));
        playerEntity.add(new PositionComponent(100,100));
        playerEntity.add(new VelocityComponent(150f,0.75f));
        playerEntity.add(new DebugColorComponent(new Color(76f/255f, 176/255f, 186f/255f,1)));
        playerEntity.add(new CollisionComponent(CollisionComponent.Shape.CIRCLE, 10));

        addEntity(playerEntity);

        addSystem(InputSystem.getInstance());
        addSystem(new MovementSystem());
        addSystem(new DebugRenderSystem(renderer));


    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    public void resize(int width, int height) {
        fv.update(width,height);
    }
}
