package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Profiles currently only hold keymappings
 */
public class Profile {

    private HashMap<Integer, Hotkey.GameHotkey> gameHotkeys = new HashMap<Integer, Hotkey.GameHotkey>();
    private String test;

    Profile(String name) {
        if(Gdx.app.getPreferences(name).contains(Strings.PREFERENCES_PROFILE)) {
            loadProfile(name);
        } else {
            createNewProfile(name);
        }
    }

    Profile() {
        initDefaultHotkeys();
        saveProfile();
    }

    private void createNewProfile(String name) {

        Preferences pref = Gdx.app.getPreferences(name);

        initDefaultHotkeys();

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

        Preferences prefs = Gdx.app.getPreferences(Strings.PREFERENCES);
        prefs.putString(Strings.PREFERENCES_PROFILE, gson.toJson(this));
    }

    private void loadProfile(String name) {
        Preferences prefs = Gdx.app.getPreferences(name);
        System.out.println(prefs.getString(Strings.PREFERENCES_PROFILE));
    }


    public void setName(String test) {
        this.test = test;
    }

    public HashMap<Integer, Hotkey.GameHotkey> getGameHotkeys() {
        return gameHotkeys;
    }
}
