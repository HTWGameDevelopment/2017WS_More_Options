package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;

/**
 * Current gameconfig | gamedata
 */
public class GameState {

    private static GameState ourInstance = new GameState();
    private Profile gameProfile;

    private ArrayList<Player> playerList = new ArrayList<Player>();

    public static GameState getInstance() {
        return ourInstance;
    }

    private GameState() {

        Preferences pref = Gdx.app.getPreferences(Strings.PREFERENCES);
        if(!pref.contains(Strings.PREFERENCES_PROFILE)) {
            gameProfile = new Profile();
        } else {
            gameProfile = new Profile(pref.getString(Strings.PREFERENCES_PROFILE));
        }

    }

    public Profile getGameProfile() {
        return gameProfile;
    }

    public void addPlayer(Player player) {
        playerList.add(player);
    }
}
