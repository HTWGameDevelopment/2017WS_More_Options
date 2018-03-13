package com.moreoptions.prototype;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.google.gson.Gson;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Strings;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;
import com.moreoptions.prototype.gameEngine.util.dataCollector.ApiRequest;
import com.moreoptions.prototype.gameEngine.util.dataCollector.LoginDetails;

import java.util.HashMap;

/**
 * A screen that helps user with login in.
 */
public class LoginScreen implements Screen {

    private Stage stage;

    private Table loginFrame;
    private Skin skin;

    //Parts
    private Label errorMessage;
    private TextButton backButton;
    private TextButton registerButton;
    private TextButton loginButton;

    private TextField email;
    private TextField pw;

    private Gson gson = new Gson();

    private MoreOptions moreOptions;

    public LoginScreen(MoreOptions moreOptions) {
        skin = AssetLoader.getInstance().getSkin();
        stage = new Stage();
        setupTextFields();
        setupButtons();
        stitchUITogether();
        this.moreOptions = moreOptions;

    }

    private void setupTextFields() {
        email = new TextField("", skin);
        email.setMessageText("Email");

        pw = new TextField("", skin);
        pw.setPasswordCharacter('*');
        pw.setMessageText("Password");
        pw.setPasswordMode(true);
    }

    private void setupButtons() {
        backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            moreOptions.showFirstScreen();
            super.clicked(event, x, y);
        }
        });

        registerButton = new TextButton("Register",skin);
        registerButton.setName("Register");


        loginButton = new TextButton("Login", skin);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                register(email.getText(), pw.getText());
                disableInput();

                showErrorText("Processing Register!");
                super.clicked(event, x, y);
            }
        });

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                login(email.getText(), pw.getText());
                disableInput();
                showErrorText("Processing Login!");
                super.clicked(event, x, y);
            }
        });

        loginButton.setName("Login");
    }

    private void disableInput() {
        //Gdx.input.setInputProcessor(null);
    }


    private void stitchUITogether() {
        loginFrame = new Table();
        loginFrame.debug();
        loginFrame.setFillParent(true);

        loginFrame.add(email);
        loginFrame.add(pw);
        loginFrame.row();
        loginFrame.add(loginButton);
        loginFrame.add(registerButton);

        errorMessage = new Label("",skin);
        errorMessage.getStyle().fontColor = Color.WHITE;
        loginFrame.row();
        loginFrame.add(backButton);
        loginFrame.add(errorMessage).padTop(50).align(Align.center);
        stage.addActor(loginFrame);
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

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().setScreenSize(width, height);
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



    private void register(final String name, final String password) {
        ApiRequest.register(name, password, new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if(httpResponse.getStatus().getStatusCode() != HttpStatus.SC_OK) {
                    showErrorText(httpResponse.getResultAsString());
                    enableInput();
                } else {
                    showErrorText(httpResponse.getResultAsString());
                    updateData(name, ApiRequest.hash(name,password));

                    enableInput();

                }

            }

            @Override
            public void failed(Throwable t) {
                showErrorText("Couldn't reach server.");
                enableInput();
            }

            @Override
            public void cancelled() {
                showErrorText("Couldn't reach server.");
                enableInput();
            }
        });
    }


    private void updateData(String username, String passwordhash) {
        Preferences preferences = Gdx.app.getPreferences(Strings.PREFERENCES);
        preferences.putString(Strings.USER_ACCOUNT, username);
        preferences.putString(Strings.USER_PASSWORD_HASH, passwordhash);
        preferences.flush();

    }

    private void login(final String name, String password) {
        final String passwordHash = ApiRequest.hash(name, password);
        ApiRequest.login(new LoginDetails(name, passwordHash), new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if(httpResponse.getStatus().getStatusCode() != HttpStatus.SC_OK) {
                    showErrorText(httpResponse.getResultAsString() + "| " + httpResponse.getStatus().getStatusCode());


                } else {
                    updateData(name, passwordHash);                                                                   //This checks if the online state is more recent than the local state.
                    showErrorText(httpResponse.getResultAsString());
                    moreOptions.showMenuScreen();
                }
            }

            @Override
            public void failed(Throwable t) {
                showErrorText("Couldn't reach server.");
                enableInput();
            }

            @Override
            public void cancelled() {
                showErrorText("Couldn't reach server.");
                enableInput();
            }
        });
    }

    private void updateSaveGame() {
        ApiRequest.getLatestSaveGame(null, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
                    GameState.getInstance().loadCloudProfile(httpResponse.getResultAsString());
                } else {
                    showErrorText("Couldn't update local savegame." + httpResponse.getStatus().getStatusCode());
                }
            }

            @Override
            public void failed(Throwable t) {
                showErrorText("Couldn't update local savegame.");
            }

            @Override
            public void cancelled() {
                showErrorText("Couldn't update local savegame.");
            }
        });
    }

    private void enableInput() {
        //Gdx.input.setInputProcessor(stage);
    }

    private void showErrorText(String s) {
        errorMessage.setText(s);
    }

    private String parseMessage(String s) {

        HashMap<String, String> results = gson.fromJson(s, HashMap.class);
        return results.get(Consts.Network.BODY);
    }
}
