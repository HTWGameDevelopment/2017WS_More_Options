package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by denwe on 06.01.2018.
 */
public class SoundDatabase {
    private static SoundDatabase ourInstance = new SoundDatabase();
    private HashMap<String, Music> musicMap = new HashMap<String, Music>();

    public static SoundDatabase getInstance() {
        return ourInstance;
    }

    private HashMap<String, ArrayList<Sound>> soundMap = new HashMap<String, ArrayList<Sound>>();
    private Random random = new Random();

    private String TAG = "SOUNDSYSTEM";

    private Music currentSong = null;

    private SoundDatabase() {

    }

    public void playSound(String sound) {
        ArrayList<Sound> soundArrayList = soundMap.get(sound);
        if (!soundArrayList.isEmpty()) {
            soundArrayList.get(random.nextInt(soundArrayList.size())).play(0.01f);
        } else {
            Gdx.app.log(TAG, "Missing soundfile for: "+ sound);
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

    public void playMusic(String sound) {

        if(currentSong != null) {
            currentSong.stop();
        }
        if(musicMap.containsKey(sound)) {
            musicMap.get(sound).setVolume(0.01f);
            musicMap.get(sound).setLooping(true);
            musicMap.get(sound).play();
            currentSong = musicMap.get(sound);
        } else {
            System.out.println("music not found: " + sound);
        }
    }

    public void pauseMusic() {
        currentSong.pause();
    }

    public void registerMusic(String tag, Music m) {
        if(musicMap.containsKey(tag)) {
            //do nothing
        } else {
            System.out.println("Registered Music: " +tag);


            musicMap.put(tag, m);
        }


    }

    public void unpauseMusic() {

        if(currentSong != null) {
            currentSong.play();
        }
    }
}
