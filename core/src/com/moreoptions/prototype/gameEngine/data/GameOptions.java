package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.moreoptions.prototype.gameEngine.util.dataCollector.LoginDetails;


/**
 * Created by denwe on 14.03.2018.
 */
public class GameOptions {

    static Preferences preferences = Gdx.app.getPreferences(Strings.PREFERENCES);

    public static boolean isOnlineEnabled() {
        return preferences.getBoolean(Strings.ONLINE_FEATURES, false);
    }

    public static boolean hasLoginDetails() {
        return preferences.contains(Strings.USER_ACCOUNT) && preferences.contains(Strings.USER_PASSWORD_HASH);
    }

    public static LoginDetails getLoginDetails() {
        if(hasLoginDetails())
        return new LoginDetails(preferences.getString(Strings.USER_ACCOUNT) , preferences.getString(Strings.USER_PASSWORD_HASH));

        return null;
    }
}
