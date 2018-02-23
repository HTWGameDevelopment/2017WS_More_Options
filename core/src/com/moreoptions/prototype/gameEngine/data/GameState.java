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

    private GameState() {/*
        Preferences pref = Gdx.app.getPreferences(Strings.PREFERENCES);
        if(!pref.contains(Strings.PREFERENCES_PROFILE)) {
            System.out.println("No profile found! Creating new one!");
            gameProfile = new Profile(Strings.PREFERENCES_PROFILE);

        } else {
            Gson gson = new Gson();
            Profile profile = gson.fromJson(pref.getString(Strings.PREFERENCES_PROFILE), Profile.class);
            profile.setIgnored(false);
            gameProfile = profile;
            //
            System.out.println("load profile from memory" + gameProfile.toString());
        }*/
    }

    public boolean doesLocalGameStateExist() {
        Preferences pref = Gdx.app.getPreferences(Strings.PREFERENCES);
        return (pref.contains(Strings.PREFERENCES_PROFILE));
    }

    public void loadLocalGameState() {
        Preferences pref = Gdx.app.getPreferences(Strings.PREFERENCES);
        Gson gson = new Gson();
        Profile profile = gson.fromJson(pref.getString(Strings.PREFERENCES_PROFILE), Profile.class);
        profile.setIgnored(false);
        gameProfile = profile;
        System.out.println("load profile from memory" + gameProfile.toString());
    }

    public void loadCloudProfile(String s) {
        Gson gson = new Gson();
        Profile profile = gson.fromJson(s, Profile.class);
        profile.setIgnored(false);
        gameProfile = profile;
        System.out.println("load profile from cloud" + gameProfile.toString());
    }

    public void createNewProfile() {
        gameProfile = new Profile(Strings.PREFERENCES_PROFILE);
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

    public void clearInput() {
        for(Player p : playerList) {
            p.getInputState().reset();
        }
    }

    public void setGameProfile(Profile gameProfile) {
        this.gameProfile = gameProfile;
    }

    public void disableOnlineFeatures() {
        //TODO: IMPL
    }
}
