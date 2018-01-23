package com.moreoptions.prototype;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.google.gson.Gson;
import com.moreoptions.prototype.gameEngine.data.Strings;
import com.moreoptions.prototype.gameEngine.util.dataCollector.ApiRequest;

import java.util.HashMap;

/**
 * Created by denwe on 22.01.2018.
 */
public class LoginScreen implements Screen {

    Stage stage;

    Table loginFrame;
    Skin skin;
    Label errorMessage;

    Gson gson = new Gson();

    MoreOptions moreOptions;

    public LoginScreen(MoreOptions moreOptions) {
        skin = new Skin(Gdx.files.internal("comic/skin/comic-ui.json"));
        stage = new Stage();
        setupLoginDialog(stage);
        this.moreOptions = moreOptions;

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            moreOptions.showStartScreen();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void setupLoginDialog(Stage stage) {
        loginFrame = new Table();
        loginFrame.debug();
        loginFrame.setFillParent(true);


        final TextField email = new TextField("", skin);
        email.setMessageText("Email");
        final TextField pw = new TextField("", skin);
        pw.setPasswordCharacter('*');
        pw.setMessageText("Password");
        pw.setPasswordMode(true);

        TextButton backButton = new TextButton("Back", skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                moreOptions.showStartScreen();
                super.clicked(event, x, y);
            }
        });

        TextButton registerButton = new TextButton("Register",skin);
        registerButton.setName("Register");


        TextButton loginButton = new TextButton("Login", skin);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                register(email.getText(), pw.getText());
                super.clicked(event, x, y);
            }
        });

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                login(email.getText(), pw.getText());
                super.clicked(event, x, y);
            }
        });

        loginButton.setName("Login");
        loginFrame.add(email);
        loginFrame.add(pw);
        loginFrame.row();
        loginFrame.add(loginButton);
        loginFrame.add(registerButton);


        errorMessage = new Label("TEST",skin);
        errorMessage.getStyle().fontColor = Color.WHITE;
        loginFrame.row();
        loginFrame.add(backButton);
        loginFrame.add(errorMessage).padTop(50).align(Align.center);
        stage.addActor(loginFrame);
    }


    public void register(final String name, final String password) {
        ApiRequest.register(name, password, new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if(httpResponse.getStatus().getStatusCode() == 400) {
                    showErrorText(httpResponse.getResultAsString());

                } else {
                    showErrorText(httpResponse.getResultAsString());
                    updateData(name, ApiRequest.hash(name,password));
                    showGreenLight();


                }
            }

            @Override
            public void failed(Throwable t) {
                showErrorText("Couldn't reach server.");
            }

            @Override
            public void cancelled() {
                showErrorText("Couldn't reach server.");
            }
        });
    }

    private void showGreenLight() {
        //moreOptions.getMainMenu().showGreenLight();
    }


    private void updateData(String username, String passwordhash) {
        Preferences preferences = Gdx.app.getPreferences(Strings.PREFERENCES);
        preferences.putString(Strings.USER_ACCOUNT, username);
        preferences.putString(Strings.USER_PASSWORD_HASH, passwordhash);
        preferences.flush();
    }

    private void login(String text, String text1) {
        ApiRequest.login(text, text1, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if(httpResponse.getStatus().getStatusCode() == 400) {
                    showErrorText(httpResponse.getResultAsString());
                } else {

                    showErrorText(httpResponse.getResultAsString());
                }
            }

            @Override
            public void failed(Throwable t) {
                showErrorText("Couldn't reach server.");
            }

            @Override
            public void cancelled() {

            }
        });
    }

    private void showErrorText(String s) {
        HashMap<String, String> results = gson.fromJson(s, HashMap.class);

        errorMessage.setText(results.get("error"));
    }
}
