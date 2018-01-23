package com.moreoptions.prototype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.google.gson.Gson;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.Strings;
import com.moreoptions.prototype.gameEngine.util.dataCollector.ApiRequest;

import java.util.HashMap;

/**
 * Created by denwe on 10.11.2017.
 */
public class StartGameScreen implements Screen {

    private Stage stage;
    private MoreOptions moreOptions;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private FitViewport viewport;

    private Table loginFrame;

    Image loginIndicatorRed;
    Image loginIndicatorGreen;

    Skin skin;
    Gson gson = new Gson();


    Label errorMessage;
    private Table greenFrame;
    private Table table;


    public StartGameScreen(final MoreOptions moreOptions) {

        this.moreOptions = moreOptions;
        skin = new Skin(Gdx.files.internal("comic/skin/comic-ui.json"));
        stage = new Stage();
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(true, true);

        TextButton newGameButton = new TextButton("New Game", skin);

        newGameButton.addListener(new ChangeListener() {
                                      @Override
                                      public void changed(ChangeEvent event, Actor actor) {
                                          startGame();
                                      }
                                  }
        );

        TextButton exitGameButton = new TextButton("Exit", skin);

        exitGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                super.clicked(event, x, y);
            }
        });

        table.add(newGameButton);
        table.row();
        table.add(exitGameButton);
        stage.addActor(table);

        errorMessage = new Label("",skin);
        //setupLoginDialog();
        setupGreenFrame();

        setupOnlineFeatures(table);
    }

    private void setupGreenFrame() {
        greenFrame = new Table();
        greenFrame.add(new Label("ALLES KLAR!", skin));

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

        stage.getViewport().update(width,height);

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

    public void startGame() {
        moreOptions.showDungeon();
    }

    public void setupOnlineFeatures(final Table table) {

        loginIndicatorRed = new Image(new Texture(Gdx.files.internal("images/RoundCircle.png")));
        loginIndicatorRed.setSize(15,15);
        loginIndicatorRed.setAlign(Align.bottomRight);
        loginIndicatorRed.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                moreOptions.showLoginScreen();
                super.clicked(event, x, y);
            }
        });

        loginIndicatorGreen = new Image(new Texture(Gdx.files.internal("images/RoundCircleGreen.png")));
        loginIndicatorGreen.setSize(15,15);
        loginIndicatorGreen.setAlign(Align.bottomRight);
        loginIndicatorGreen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                moreOptions.showLoginScreen();
                super.clicked(event, x, y);
            }
        });

        stage.addActor(loginIndicatorRed);

        //First, we check if user has existing account data

        Preferences prefs = Gdx.app.getPreferences(Strings.PREFERENCES);
        if(prefs.contains(Strings.USER_ACCOUNT) && prefs.contains(Strings.USER_PASSWORD_HASH)) {
            //Yes, lets launch a thread to verify.
            String username = prefs.getString(Strings.USER_ACCOUNT);
            String pwHash = prefs.getString(Strings.USER_PASSWORD_HASH);

            disableUserInput();
            showLoadingText(table);
            System.out.println("ALLES DRIN");
            //we dont want player to do something else while verifying

            ApiRequest.login(username, pwHash, new Net.HttpResponseListener() {
                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    if(httpResponse.getStatus().getStatusCode() == Consts.Network.SUCCESS) {
                        verifyLocalSavegame();
                        showGreenLight();
                        enableUserInput();
                        System.out.println("-------");
                    } else {

                    }
                }

                @Override
                public void failed(Throwable t) {
                    enableUserInput();

                }

                @Override
                public void cancelled() {
                    enableUserInput();
                }
            });

        } else {
            showLoginDialog(table);
        }
    }

    private void showLoadingText(Table table) {

    }

    private void showLoginDialog(Table stage) {
        stage.add(loginFrame).bottom();
    }

    private void showGreenLight() {
        loginIndicatorRed.remove();
        stage.addActor(loginIndicatorGreen);
    }

    private void verifyLocalSavegame() {
        //TODO impl
    }

    private void enableUserInput() {
        //TODO impl
    }

    private void disableUserInput() {
        //TODO impl
    }

    private void setupLoginDialog() {
        loginFrame = new Table();
        loginFrame.bottom().right();
        loginFrame.debug();


        final TextField email = new TextField("", skin);
        email.setMessageText("Email");
        final TextField pw = new TextField("", skin);
        pw.setPasswordCharacter('*');
        pw.setMessageText("Password");
        pw.setPasswordMode(true);


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
        loginFrame.add(errorMessage).padTop(50).align(Align.center);
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
