package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Current gameconfig | gamedata
 */
public class GameState {

    private static GameState ourInstance = new GameState();
    private Profile gameProfile;

    private Player playerOne;
    private Player playerTwo;


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


        //TODO do this during game/world creation
        playerOne = new Player();

    }

    public Profile getGameProfile() {
        return gameProfile;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }
}
