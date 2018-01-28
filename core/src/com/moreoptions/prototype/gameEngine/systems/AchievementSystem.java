package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.moreoptions.prototype.gameEngine.components.EnemyComponent;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.util.dataCollector.ApiRequest;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventListener;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventSubscriber;

import java.util.HashMap;

/**
 * Created by Dennis on 03.01.2018.
 */
public class AchievementSystem extends EntitySystem {

    private static String ByteID = "id";
    EventSubscriber subscriber = new EventSubscriber();

    HashMap<String, AchievementListener> dataProcessors = new HashMap<String, AchievementListener>();
    HashMap<String, Float>  data = new HashMap<String, Float>();


    public void registerSaveGame() {
        subscriber.subscribe(Consts.SAVE_GAME, new EventListener() {
            @Override
            public boolean trigger(Event e) {
                ApiRequest.saveGame(GameState.getInstance().getGameProfile());
                System.out.println("Triggered AUTO_SAVE");
                return false;
            }
        });
    }


    public void registerData() {
        dataProcessors.put(Consts.Data.DOOR_STAT, new FloatAchievementListener());
        dataProcessors.put(Consts.Data.DAMAGE_DONE, new FloatAchievementListener());
        dataProcessors.put(Consts.Data.DAMAGE_TAKEN, new FloatAchievementListener());
        dataProcessors.put(Consts.Data.ENEMIES_KILLED, new FloatAchievementListener());
        dataProcessors.put(Consts.Data.HIGHEST_LEVEL, new HighestLevelListener());
        dataProcessors.put(Consts.Data.ENEMY_KILLED, new EnemyDeathTracker());
        dataProcessors.put(Consts.Data.DISTANCE_WALKED, new FloatAchievementListener());

    }

    public AchievementSystem() {
        registerData();
        registerSaveGame();
        subscriber.subscribe(Consts.ACHIEVEMENT_EVENT_ID, new EventListener() {
            @Override
            public boolean trigger(Event e) {
                System.out.println("Tracking: test");
                String id = e.getData(AchievementSystem.ByteID, String.class);
                if(dataProcessors.containsKey(id)) {
                    dataProcessors.get(id).handle(e);
                    System.out.println("Tracking: " + id);
                } else {
                    try {
                        throw new AchievementException("Untracked Data! Please add a dataprocessor for "+e.getData("id", String.class));
                    } catch (AchievementException e1) {
                        e1.printStackTrace();
                    }
                }
                return true;
            }
        });
    }

    public void init(HashMap<String, Float> data) {

    }


    private interface AchievementListener {

        boolean handle(Event e);

    }

    private class AchievementException extends Throwable {
        public AchievementException(String s) {
            super(s);
        }
    }

    private class FloatAchievementListener implements AchievementListener {
        @Override
        public boolean handle(Event e) {
            if(data.containsKey(e.getData("id", String.class))) {
                float i = data.get(e.getData("id", String.class));
                data.put(e.getData("id", String.class), i + e.getData(Consts.FLOAT, Float.class));
                GameState.getInstance().getGameProfile().getStats().putAll(data);
            } else {

                data.put(e.getData("id", String.class), e.getData(Consts.FLOAT, Float.class));
                GameState.getInstance().getGameProfile().getStats().putAll(data);
            }
            return false;
        }
    }


    private class HighestLevelListener implements AchievementListener {
        @Override
        public boolean handle(Event e) {
            String key = e.getData("id", String.class);
            float highest = data.get(e.getData(Consts.FLOAT, Float.class));
            if(data.containsKey(key)) {
                float i = data.get(key);
                highest = data.get(e.getData(Consts.FLOAT, Float.class));

                if(highest > i) {
                    data.put(key, highest);
                }

            } else {
                data.put(key, highest);
            }

            GameState.getInstance().getGameProfile().getStats().putAll(data);
            return false;
        }
    }

    private class EnemyDeathTracker implements AchievementListener {
        @Override
        public boolean handle(Event e) {
            //Entity enemy = e.getData(Consts.ENEMY_DATA, Entity.class);
            //EnemyComponent ec = enemy.getComponent(EnemyComponent.class);

            //
            //System.out.println("Untracked enemy death:" +ec.getEnemyId());

            return false;
        }
    }
}
