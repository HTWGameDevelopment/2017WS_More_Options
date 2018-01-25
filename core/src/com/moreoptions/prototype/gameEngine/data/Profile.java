package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;

/**
 * Profiles currently only hold keymappings
 */
public class Profile {

    private HashMap<Integer, Hotkey.GameHotkey> gameHotkeys = new HashMap<Integer, Hotkey.GameHotkey>();
    private Achievements achievements;
    private String name;
    private Date date;

    Profile(String name) {
        createNewProfile(name);
    }

    public Profile() {

    }

    @Override
    public String toString() {
        return achievements.toString();
    }

    private void createNewProfile(String name) {

        this.name = name;

        initDefaultHotkeys();
        achievements = new Achievements();

        saveProfile();

    }

    private void initDefaultHotkeys() {

        //Default hotkeys
        gameHotkeys.put(Input.Keys.UP, Hotkey.GameHotkey.P1_SHOOT_UP);
        gameHotkeys.put(Input.Keys.DOWN, Hotkey.GameHotkey.P1_SHOOT_DOWN);
        gameHotkeys.put(Input.Keys.LEFT, Hotkey.GameHotkey.P1_SHOOT_LEFT);
        gameHotkeys.put(Input.Keys.RIGHT, Hotkey.GameHotkey.P1_SHOOT_RIGHT);

        gameHotkeys.put(Input.Keys.W, Hotkey.GameHotkey.P1_MOVE_UP);
        gameHotkeys.put(Input.Keys.S, Hotkey.GameHotkey.P1_MOVE_DOWN);
        gameHotkeys.put(Input.Keys.A, Hotkey.GameHotkey.P1_MOVE_LEFT);
        gameHotkeys.put(Input.Keys.D, Hotkey.GameHotkey.P1_MOVE_RIGHT);

        gameHotkeys.put(Input.Keys.SPACE, Hotkey.GameHotkey.P1_USE_ITEM);
        gameHotkeys.put(Input.Keys.Q, Hotkey.GameHotkey.P1_USE_PICKUP);
        gameHotkeys.put(Input.Keys.E, Hotkey.GameHotkey.P1_USE_BOMB);

    }


    private void saveProfile() {
        Gson gson = new Gson();
        this.date = new Date();         //Sign with current date
        Preferences prefs = Gdx.app.getPreferences(Strings.PREFERENCES_PROFILE);
        prefs.putString(name, gson.toJson(this));
        System.out.println("Creating profile");
        prefs.flush();
    }



    public HashMap<Integer, Hotkey.GameHotkey> getGameHotkeys() {
        return gameHotkeys;
    }

    public Achievements getAchievements() {
        return achievements;
    }

    public void save() {
        saveProfile();
    }

    public Date getDate() {
        return date;
    }
}
