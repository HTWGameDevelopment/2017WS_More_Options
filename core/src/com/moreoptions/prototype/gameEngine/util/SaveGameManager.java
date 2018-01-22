package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Profile;
import com.moreoptions.prototype.gameEngine.data.Strings;

/**
 * Created by denwe on 18.01.2018.
 */
public class SaveGameManager {


    public Profile getMostRecent(GameState b) {

        Gson gson = new Gson();

        //First, get local Profile if its there

        Preferences prefs = Gdx.app.getPreferences(Strings.PREFERENCES);

        if(prefs.contains(Strings.PREFERENCES_PROFILE));
        return null;

    }



}
