package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Current gameconfig | gamedata
 */
public class GameState {

    private static GameState ourInstance = new GameState();
    private Profile gameProfile;

    private ArrayList<Player> playerList = new ArrayList<Player>();
    private boolean debugMode = true;

    Gson gson = new Gson();

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
        System.out.println("Checking if local state exists!" + pref.contains(Strings.PREFERENCES_PROFILE));

        return (pref.contains(Strings.PREFERENCES_PROFILE));
    }

    public void loadLocalGameState() {
        Preferences pref = Gdx.app.getPreferences(Strings.PREFERENCES);

        Profile profile = gson.fromJson(pref.getString(Strings.PREFERENCES_PROFILE), Profile.class);
        profile.setIgnored(false);
        gameProfile = profile;
        System.out.println("load profile from memory" + gameProfile.toString());
    }

    public void loadCloudProfile(String s) {

        Gson gson = new Gson();
        System.out.println(s);
        HashMap<String, String> json = gson.fromJson(s,HashMap.class);
        Profile profile = gson.fromJson(json.get("profile"), Profile.class);

        if(doesLocalGameStateExist()) {
            if(!profile.getDate().before(gameProfile.getDate())) {
                profile.setIgnored(false);
                gameProfile = profile;
                System.out.println("load profile from cloud" + gameProfile.toString());
            }
        } else {
            gameProfile = profile;

            System.out.println("load profile from cloud because no local" + gameProfile.toString());
        }

        saveProfile();
    }

    public void createNewProfile() {
        gameProfile = new Profile(Strings.PREFERENCES_PROFILE);
        saveProfile();
    }

    private void saveProfile() {
        Preferences pref = Gdx.app.getPreferences(Strings.PREFERENCES);
        pref.putString(Strings.PREFERENCES_PROFILE, gson.toJson(gameProfile));
        pref.flush();
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
