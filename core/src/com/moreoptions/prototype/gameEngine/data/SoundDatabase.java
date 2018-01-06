package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by denwe on 06.01.2018.
 */
public class SoundDatabase {
    private static SoundDatabase ourInstance = new SoundDatabase();

    public static SoundDatabase getInstance() {
        return ourInstance;
    }

    HashMap<String, ArrayList<Sound>> soundMap = new HashMap<String, ArrayList<Sound>>();
    private Random random = new Random();



    private SoundDatabase() {


    }

    public void playSound(String sound) {
        ArrayList<Sound> soundArrayList = soundMap.get(sound);
        if (!soundArrayList.isEmpty()) {
            soundArrayList.get(random.nextInt(soundArrayList.size())).play(5);
        }
    }

    public void registerSound(String tag, Sound s) {

        if(soundMap.containsKey(tag)) {
            soundMap.get(tag).add(s);
        } else {
            ArrayList<Sound> newSoundArrayList = new ArrayList<Sound>();
            newSoundArrayList.add(s);
            soundMap.put(tag, newSoundArrayList);
        }

    }

}
