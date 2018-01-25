package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.SoundDatabase;
import com.moreoptions.prototype.gameEngine.util.EventFactory;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventListener;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventSubscriber;

/**
 * Created by denwe on 06.01.2018.
 */
public class SoundSystem extends EntitySystem {

    EventSubscriber soundSubscriber = new EventSubscriber();

    public SoundSystem() {

        soundSubscriber.subscribe(Consts.GAME_OVER, new EventListener() {
            @Override
            public boolean trigger(Event e) {
                SoundDatabase.getInstance().playSound(Consts.Sound.GAME_OVER_SOUND);
                return false;
            }
        });


        soundSubscriber.subscribe(Consts.TITLE_MUSIC, new EventListener() {
            @Override
            public boolean trigger(Event e) {
                SoundDatabase.getInstance().playMusic(Consts.Sound.TILE_MUSIC);
                return false;
            }
        });
    }

}
