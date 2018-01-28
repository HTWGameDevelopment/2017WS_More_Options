package com.moreoptions.prototype.gameEngine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.moreoptions.prototype.DungeonScreen;
import com.moreoptions.prototype.gameEngine.components.DoorComponent;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Player;
import com.moreoptions.prototype.gameEngine.data.SoundDatabase;
import com.moreoptions.prototype.gameEngine.input.GameInputProcessor;
import com.moreoptions.prototype.gameEngine.systems.*;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;
import com.moreoptions.prototype.gameEngine.util.DataTracker;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventBus;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventListener;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventSubscriber;
import com.moreoptions.prototype.gameEngine.level.LevelManager;
import com.moreoptions.prototype.userInterface.UserInterface;

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
    private Stage stage;
    private UserInterface userInterface;
    private BitmapFont font;

    private EventSubscriber subscriber = new EventSubscriber();

    private ProgressBar healthBar;
    private DungeonScreen parentScreen;

    int currentLevel = 0;

    private GameWorld() {
        uiSetup();
        demoSetup();

        subscriber.subscribe(Consts.GAME_OVER, new EventListener() {
            @Override
            public boolean trigger(Event e) {
                removeAllEntities();
                GameState.getInstance().reset();
                return false;
            }
        });

        subscriber.subscribe(Consts.ADVANCE_LEVEL_EVENT, new EventListener() {
            @Override
            public boolean trigger(Event e) {
                currentLevel += 1;
                SoundDatabase.getInstance().playSound("completetask");
                e.getData("door", DoorComponent.class).setState(DoorComponent.DOOR_CLOSED);
                DataTracker.trackFloatData(Consts.Data.HIGHEST_LEVEL, currentLevel);
                levelManager.generateNewLevel(10, 10, 10 + currentLevel);
                return false;
            }
        });

    }


    private void uiSetup() {

        stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal("comic/skin/comic-ui.json"));
        healthBar = new ProgressBar(0, 3, 1, false, skin);
        healthBar.setValue(3);
        stage.addActor(healthBar);
        stage.setDebugAll(true);

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

        font = AssetLoader.getInstance().getAssetManager().get("fonts/RobotoSlab-Bold.ttf", BitmapFont.class);

        processor = new GameInputProcessor(camera);
        Gdx.input.setInputProcessor(processor);

        Player p = new Player();
        processor.addPlayer(p);

        userInterface = new UserInterface(fv, batch, p);
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
        addSystem(new PlayerSystem(healthBar));
        addSystem(new EnemySystem());
        addSystem(new AchievementSystem());
        addSystem(new SoundSystem());
        //addSystem(new BlendTestSystem(renderer, camera));
        //addSystem(new QuadTreeDebug(batch, renderer));
        levelManager = new LevelManager(this);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        //stage.draw();
        userInterface.update();
        userInterface.draw();
    }

    public void updateInput() {
        getSystem(AchievementSystem.class).init(GameState.getInstance().getGameProfile().getStats());
        Gdx.input.setInputProcessor(processor);
        GameState.getInstance().clearInput();
    }

    public LevelManager getRoomManager() {
        return levelManager;
    }

    public static GameWorld getInstance() {
        if(gameEngine == null) gameEngine = new GameWorld();
        return gameEngine;
    }

    @Override
    public void removeAllEntities() {

        super.removeAllEntities();
    }

    public void generateNewLevel() {
        levelManager.generateNewLevel(10,10,10);
    }

    public void setParentScreen(DungeonScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

}
