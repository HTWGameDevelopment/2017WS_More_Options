package com.moreoptions.prototype.gameEngine.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.InputState;
import com.moreoptions.prototype.gameEngine.data.Player;

/**
 * Implementation of the Libgdx Inputprocessor, called before the render() step.
 *
 */
public class GameInputProcessor implements InputProcessor {

    private OrthographicCamera camera;
    private InputState p1;

    public GameInputProcessor(OrthographicCamera camera) {
        this.camera = camera;
    }

    private int playerCount = 0;

    public void addPlayer(Player p) {
        if(playerCount == 0) {
            p1 = p.getInputState();
        }
    }

    @Override
    public boolean keyDown(int keycode) {

        if(GameState.getInstance().getGameProfile().getGameHotkeys().get(keycode) == null) {
            return true;
        }

        switch(GameState.getInstance().getGameProfile().getGameHotkeys().get(keycode)) {
            case P1_MOVE_UP:
                p1.setMoveUp(true);
                break;
            case P1_MOVE_DOWN:
                p1.setMoveDown(true);
                break;
            case P1_MOVE_LEFT:
                p1.setMoveLeft(true);
                break;
            case P1_MOVE_RIGHT:
                p1.setMoveRight(true);
                break;
            case P1_USE_ITEM:
                p1.setUseItem(true);
                break;
            case P1_SHOOT_UP:
                p1.setShootUp(true);
                break;
            case P1_SHOOT_DOWN:
                p1.setShootDown(true);
                break;
            case P1_SHOOT_LEFT:
                p1.setShootLeft(true);
                break;
            case P1_SHOOT_RIGHT:
                p1.setShootRight(true);
                break;
            case P1_USE_BOMB:
                p1.setUseBomb(true);
                break;
            case P1_PAUSE:
                //Pause
                break;
            case P1_USE_PICKUP:
                p1.setUsePickup(true);
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(GameState.getInstance().getGameProfile().getGameHotkeys().get(keycode) == null) {
            return true;
        }

        switch(GameState.getInstance().getGameProfile().getGameHotkeys().get(keycode)) {
            case P1_MOVE_UP:
                p1.setMoveUp(false);
                break;
            case P1_MOVE_DOWN:
                p1.setMoveDown(false);
                break;
            case P1_MOVE_LEFT:
                p1.setMoveLeft(false);
                break;
            case P1_MOVE_RIGHT:
                p1.setMoveRight(false);
                break;
            case P1_USE_ITEM:
                p1.setUseItem(false);
                break;
            case P1_SHOOT_UP:
                p1.setShootUp(false);
                break;
            case P1_SHOOT_DOWN:
                p1.setShootDown(false);
                break;
            case P1_SHOOT_LEFT:
                p1.setShootLeft(false);
                break;
            case P1_SHOOT_RIGHT:
                p1.setShootRight(false);
                break;
            case P1_USE_BOMB:
                p1.setUseBomb(false);
                break;
            case P1_PAUSE:
                //Pause
                break;
            case P1_USE_PICKUP:
                p1.setUsePickup(false);
                break;
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
