package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventListener;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventSubscriber;

import java.util.HashMap;

/**
 * Created by Dennis on 03.01.2018.
 */
public class AchievementSystem extends EntitySystem{

    EventSubscriber subscriber = new EventSubscriber();

    HashMap<String, AchievementListener> dataProcessors = new HashMap<String, AchievementListener>();
    HashMap<String, Integer>            dataInt         = new HashMap<String, Integer>();

    public void registerData() {
        dataProcessors.put(Consts.DOOR_STAT, new IntAchievementListener());

    }

    public AchievementSystem() {
        registerData();
        subscriber.subscribe(Consts.ACHIEVEMENT_EVENT_ID, new EventListener() {
            @Override
            public void trigger(Event e) {

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
            if(dataInt.containsKey(e.getData("id", String.class))) {
                int i = dataInt.get(e.getData("id", String.class));
                dataInt.put(e.getData("id", String.class), i + e.getData("int", Integer.class));
            }
            return false;
        }
    }
}
