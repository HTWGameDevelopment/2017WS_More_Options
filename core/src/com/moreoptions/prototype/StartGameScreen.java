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
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.google.gson.Gson;
import com.moreoptions.prototype.gameEngine.data.*;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;
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

    private Image loginIndicatorRed;
    private Image loginIndicatorGreen;
    private Image currentLoginIndicator;

    private Image logo;

    private Skin skin;
    private Gson gson = new Gson();


    Label errorMessage;
    private Table table;


    public StartGameScreen(final MoreOptions moreOptions) {

        this.moreOptions = moreOptions;
        skin = AssetLoader.getInstance().getSkin();
        logo = new Image(new Texture(Gdx.files.internal("images/more_options.png")));
        stage = new Stage();
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(true, true);

        TextButton newGameButton = new TextButton(Strings.Menu.NEW_GAME, skin);

        newGameButton.addListener(new ChangeListener() {
                                      @Override
                                      public void changed(ChangeEvent event, Actor actor) {
                                          startGame();
                                      }
                                  }
        );

        TextButton exitGameButton = new TextButton(Strings.Menu.EXIT_GAME, skin);

        exitGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                super.clicked(event, x, y);
            }
        });

        TextButton statsButton = new TextButton("Stats", skin);

        statsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                moreOptions.showStatsScreen();
                super.clicked(event, x, y);
            }
        });


        table.add(logo);
        table.row();
        table.add(newGameButton);
        table.row();
        table.add(statsButton);
        table.row();
        table.add(exitGameButton);
        stage.addActor(table);

        errorMessage = new Label("",skin);
        //setupLoginDialog();

    }


    @Override
    public void show() {
        SoundDatabase.getInstance().playMusic("intro");
        verifyLogin();
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


    private void verifyLogin() {
        Preferences prefs = Gdx.app.getPreferences(Strings.PREFERENCES);
        if(prefs.contains(Strings.USER_ACCOUNT) && prefs.contains(Strings.USER_PASSWORD_HASH)) {
            //Yes, lets launch a thread to verify.
            String username = prefs.getString(Strings.USER_ACCOUNT);
            String pwHash = prefs.getString(Strings.USER_PASSWORD_HASH);

            disableUserInput();
            showLoadingText(table);
            //we dont want player to do something else while verifying

            ApiRequest.login(username, pwHash, new Net.HttpResponseListener() {
                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    if(httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
                        verifyLocalSavegame();
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

    private void verifyLocalSavegame() {
        ApiRequest.getLatestSaveGame(GameState.getInstance().getGameProfile(), new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                System.out.println("----CHECKED SERVER-----");
                System.out.println(httpResponse.getStatus().getStatusCode());

                HashMap<String, String> p = gson.fromJson(httpResponse.getResultAsString(), HashMap.class);
                System.out.println("Server!!" + p.get("serverprofile"));

                Profile profile = gson.fromJson(p.get("serverprofile"), Profile.class);
                GameState.getInstance().setGameProfile(profile);

                System.out.println("----CHECKED SERVER");


            }

            @Override
            public void failed(Throwable t) {
                System.out.println("___");
            }

            @Override
            public void cancelled() {

            }
        });
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
