package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
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

    EventSubscriber subscriber = new EventSubscriber();

    HashMap<String, AchievementListener> dataProcessors = new HashMap<String, AchievementListener>();
    HashMap<String, Float>            data         = new HashMap<String, Float>();


    public void registerSaveGame() {
        subscriber.subscribe(Consts.SAVE_GAME, new EventListener() {
            @Override
            public boolean trigger(Event e) {

                ApiRequest.saveGame(GameState.getInstance().getGameProfile());
                System.out.println("Triggered");
                return false;
            }
        });
    }


    public void registerData() {
        dataProcessors.put(Consts.DOOR_STAT, new IntAchievementListener());

    }

    public AchievementSystem() {
        registerData();
        registerSaveGame();
        subscriber.subscribe(Consts.ACHIEVEMENT_EVENT_ID, new EventListener() {
            @Override
            public boolean trigger(Event e) {
                if(dataProcessors.containsKey(e.getData("id", String.class))) {
                    dataProcessors.get(e.getData("id", String.class)).handle(e);
                    System.out.println("Tracked" + e.getData("id", String.class));
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

    private interface AchievementListener {

        boolean handle(Event e);

    }

    private class AchievementException extends Throwable {
        public AchievementException(String s) {
            super(s);
        }
    }

    private class IntAchievementListener implements AchievementListener {
        @Override
        public boolean handle(Event e) {
            if(data.containsKey(e.getData("id", String.class))) {
                float i = data.get(e.getData("id", String.class));
                data.put(e.getData("id", String.class), i + e.getData("int", Integer.class));
            }
            return false;
        }
    }


}
