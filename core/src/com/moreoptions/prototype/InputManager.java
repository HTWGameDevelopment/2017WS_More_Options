package com.moreoptions.prototype;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.moreoptions.prototype.gameEngine.systems.InputSystem;

/**
 * Created by Bodo on 23.10.2017.
 */
public class InputManager implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP: {
                InputSystem.getInstance().setUp(true);
                break;
            }

            case Input.Keys.DOWN: {
                InputSystem.getInstance().setDown(true);
                break;
            }

            case Input.Keys.LEFT: {
                InputSystem.getInstance().setLeft(true);
                break;
            }

            case Input.Keys.RIGHT: {
                InputSystem.getInstance().setRight(true);
                break;
            }

        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.UP: {
                InputSystem.getInstance().setUp(false);
                break;
            }

            case Input.Keys.DOWN: {
                InputSystem.getInstance().setDown(false);
                break;
            }

            case Input.Keys.LEFT: {
                InputSystem.getInstance().setLeft(false);
                break;
            }

            case Input.Keys.RIGHT: {
                InputSystem.getInstance().setRight(false);
                break;
            }

        }
        return false;
    }


    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}