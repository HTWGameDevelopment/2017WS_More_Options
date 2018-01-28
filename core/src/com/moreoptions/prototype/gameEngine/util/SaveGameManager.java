package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Profile;
import com.moreoptions.prototype.gameEngine.data.Strings;
import com.moreoptions.prototype.gameEngine.util.dataCollector.ApiRequest;

/**
 * Created by denwe on 18.01.2018.
 */
public class SaveGameManager {


    public static void getMostRecentSaveGame(String name, String password, Net.HttpResponseListener listener) {

        ApiRequest.getProfile(name, password, listener);

    }

    public static void verifyLoginData(String name, String password, Net.HttpResponseListener listener) {
        ApiRequest.login(name, password, listener);
    }





}
