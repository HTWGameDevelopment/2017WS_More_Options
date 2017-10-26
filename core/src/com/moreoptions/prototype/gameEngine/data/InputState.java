package com.moreoptions.prototype.gameEngine.data;

/**
 * Current state of the keyboard obtained by polling.
 *
 * Not sure if this is the way to go or adding a smarter InputEvent system. Lets find out while prototyping.
 *
 */
public class InputState {

    private boolean moveRight = false;
    private boolean moveLeft = false;
    private boolean moveUp = false;
    private boolean moveDown = false;

    private boolean shootUp     = false;
    private boolean shootDown   = false;
    private boolean shootLeft   = false;
    private boolean shootRight  = false;

    private boolean usePickup = false;
    private boolean useBomb = false;
    private boolean useItem = false;

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public boolean isShootUp() {
        return shootUp;
    }

    public void setShootUp(boolean shootUp) {
        this.shootUp = shootUp;
    }

    public boolean isShootDown() {
        return shootDown;
    }

    public void setShootDown(boolean shootDown) {
        this.shootDown = shootDown;
    }

    public boolean isShootLeft() {
        return shootLeft;
    }

    public void setShootLeft(boolean shootLeft) {
        this.shootLeft = shootLeft;
    }

    public boolean isShootRight() {
        return shootRight;
    }

    public void setShootRight(boolean shootRight) {
        this.shootRight = shootRight;
    }

    public boolean isUsePickup() {
        return usePickup;
    }

    public void setUsePickup(boolean usePickup) {
        this.usePickup = usePickup;
    }

    public boolean isUseBomb() {
        return useBomb;
    }

    public void setUseBomb(boolean useBomb) {
        this.useBomb = useBomb;
    }

    public boolean isUseItem() {
        return useItem;
    }

    public void setUseItem(boolean useItem) {
        this.useItem = useItem;
    }
}
