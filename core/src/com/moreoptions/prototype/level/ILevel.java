package com.moreoptions.prototype.level;

/**
 * Created by Andreas on 30.10.2017.
 */
public interface ILevel {

    void createMap();

    void makeRooms();

    void makeSpecialRooms();

    void makeNormalRooms();

    void makeStartingRoom();

    void makeBossRoom();

    void makeVendorRoom();

    void makeItemRoom();

    void makeSecretRoom();

    void printMap();

    void makeRoomNorth(int x, int y, int kind);

    void makeRoomEast(int x, int y, int kind);

    void makeRoomSouth(int x, int y, int kind);

    void makeRoomWest(int x, int y, int kind);

    void countNeighbours();

    void getPossibleSecretRooms();

    boolean checkNorthOutside(int y);

    boolean checkEastOutside(int x);

    boolean checkSouthOutside(int y);

    boolean checkWestOutside(int x);
}
