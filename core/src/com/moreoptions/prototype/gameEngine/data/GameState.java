package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Current gameconfig | gamedata
 */
public class GameState {

    private static GameState ourInstance = new GameState();
    private Profile gameProfile;

    private ArrayList<Player> playerList = new ArrayList<Player>();
    private boolean debugMode = true;

    public static GameState getInstance() {
        return ourInstance;
    }

    private GameState() {
        Preferences pref = Gdx.app.getPreferences(Strings.PREFERENCES);
        if(!pref.contains(Strings.PREFERENCES_PROFILE)) {
            System.out.println("No profile found! Creating new one!");
            gameProfile = new Profile("Test");

        } else {
            Gson gson = new Gson();
            Profile profile = gson.fromJson(pref.getString(Strings.PREFERENCES_PROFILE), Profile.class);
            gameProfile = profile;
            //
            System.out.println("load profile from memory" + gameProfile.toString());
            gameProfile.getAchievements().bosskiller = true;
            gameProfile.save();
        }
    }

    private GameState(Profile p) {
        this.gameProfile = p;
    }

    public Profile getGameProfile() {
        return gameProfile;
    }

    public void addPlayer(Player player) {
        playerList.add(player);
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void reset() {
        for(Player p : playerList) {
            p.reset();
        }
    }
}
