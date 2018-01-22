package com.moreoptions.prototype.gameEngine.util.dataCollector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.google.gson.Gson;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.Profile;
import com.moreoptions.prototype.gameEngine.data.Strings;
import sun.misc.BASE64Encoder;

import java.util.HashMap;

/**
 * Created by denwe on 16.01.2018.
 */
public class ApiRequest {

    private static String serverAdress = Consts.Network.SERVER_ADRESS;

    private static Gson gson = new Gson();
    private static BASE64Encoder encoder = new BASE64Encoder();

    private static HttpRequestBuilder b = new HttpRequestBuilder();

    public static void register(String name, String password, Net.HttpResponseListener listener) {

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("username", name);

        String q = hash(name, password);

        data.put("password", q);

        final Net.HttpRequest request = b.newRequest().method(Net.HttpMethods.POST).url(serverAdress + "/register/").header("Content-Type","application/json").build();
        request.setContent(gson.toJson(data));
        Gdx.net.sendHttpRequest(request, listener);
    }

    public static String hash(String name, String password) {
        String salt = Consts.Network.SALT + name;
        String q = encoder.encode( (salt + password).getBytes());
        return q;
    }

    public static void login(String name, String password, Net.HttpResponseListener listener) {

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("username", name);

        String salt = Consts.Network.SALT + name;
        String q = encoder.encode( (salt + password).getBytes());

        data.put("password", q);

        final Net.HttpRequest request = b.newRequest().method(Net.HttpMethods.POST).url(serverAdress + "/login/").header("Content-Type","application/json").build();
        request.setContent(gson.toJson(data));
        Gdx.net.sendHttpRequest(request, listener);
    }


    public static void saveGame(Profile gameProfile) {

        gameProfile.save();

        if(isLoggedIn()) {
            Preferences prefs = Gdx.app.getPreferences(Strings.PREFERENCES);
            String username = prefs.getString(Strings.USER_ACCOUNT);
            String pw = prefs.getString(Strings.USER_PASSWORD_HASH);
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("profile", gson.toJson(gameProfile));
            data.put("username", username);
            data.put("password", pw);

            System.out.println(username + " | " + pw);

            final Net.HttpRequest request = b.newRequest().method(Net.HttpMethods.POST).url(serverAdress + "/state/").header("Content-Type","application/json").build();
            request.setContent(gson.toJson(data));
            Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    System.out.println(httpResponse.getStatus().getStatusCode());
                }

                @Override
                public void failed(Throwable t) {

                }

                @Override
                public void cancelled() {

                }
            });
        }


    }

    public static boolean isLoggedIn() {
        Preferences prefs = Gdx.app.getPreferences(Strings.PREFERENCES);
        return prefs.contains(Strings.USER_ACCOUNT) && prefs.contains(Strings.USER_PASSWORD_HASH);
    }
}
