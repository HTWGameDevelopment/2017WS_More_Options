package com.moreoptions.prototype.level;

/**
 * Created by Andreas on 28.10.2017.
 */
public class Room {
    private static final int EMPTY_ROOM = 0;
    private static final int STARTING_ROOM = 1;
    private static final int NORMAL_ROOM = 2;
    private static final int BOSS_ROOM = 3;
    private static final int VENDOR_ROOM = 4;
    private static final int ITEM_ROOM = 5;
    private static final int SECRET_ROOM = 6;

    private int xCoord;
    private int yCoord;
    private int kind;
    private int neighbours;

    private boolean isSuitableForSpecialRoom;
    private boolean isOccupied;

    public Room(int x, int y, int kind) {
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

    public boolean isSuitableForSpecialRoom() {
        return isSuitableForSpecialRoom;
    }

    public void setSuitableForSpecialRoom(boolean suitableForSpecialRoom) {
        isSuitableForSpecialRoom = suitableForSpecialRoom;
    }
}
