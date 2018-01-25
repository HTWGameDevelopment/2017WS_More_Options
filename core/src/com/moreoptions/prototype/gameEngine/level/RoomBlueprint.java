package com.moreoptions.prototype.gameEngine.level;

/**
 * Created by Andreas on 28.10.2017.
 */
public class RoomBlueprint {
    public static final int EMPTY_ROOM = 0;
    public static final int STARTING_ROOM = 1;
    public static final int NORMAL_ROOM = 2;
    public static final int BOSS_ROOM = 3;
    public static final int VENDOR_ROOM = 4;
    public static final int ITEM_ROOM = 5;
    public static final int SECRET_ROOM = 6;

    private int xCoord;
    private int yCoord;
    private int kind;
    private int neighbours;

    private boolean hasNeighbourTop = false;
    private boolean hasNeighbourBottom= false;
    private boolean hasNeighbourLeft= false;
    private boolean hasNeighbourRight= false;

    private boolean isSuitableForSpecialRoom;
    private boolean isOccupied;
    private boolean left = false;
    private boolean right = false;
    private boolean down = false;
    private boolean top = false;

    public RoomBlueprint(int x, int y, int kind) {
        this.xCoord = x;
        this.yCoord = y;
        this.kind = kind;
        this.neighbours = 0;
        this.isSuitableForSpecialRoom = false;
        this.isOccupied = false;
    }

    public int getXCoord() {
        return xCoord;
    }

    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }


    public void setNeighbours(int neighbours) {
        this.neighbours = neighbours;
    }

    public int getNeighbours() {
        return neighbours;
    }

    public boolean isHasNeighbourTop() {
        return hasNeighbourTop;
    }

    public void setHasNeighbourTop(boolean hasNeighbourTop) {
        this.hasNeighbourTop = hasNeighbourTop;
    }

    public boolean isHasNeighbourBottom() {
        return hasNeighbourBottom;
    }

    public void setHasNeighbourBottom(boolean hasNeighbourBottom) {
        this.hasNeighbourBottom = hasNeighbourBottom;
    }

    public boolean isHasNeighbourLeft() {
        return hasNeighbourLeft;
    }

    public void setHasNeighbourLeft(boolean hasNeighbourLeft) {
        this.hasNeighbourLeft = hasNeighbourLeft;
    }

    public boolean isHasNeighbourRight() {
        return hasNeighbourRight;
    }

    public void setHasNeighbourRight(boolean hasNeighbourRight) {
        this.hasNeighbourRight = hasNeighbourRight;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isTop() {
        return top;
    }
}
