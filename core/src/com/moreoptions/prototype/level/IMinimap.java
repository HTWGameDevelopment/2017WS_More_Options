package com.moreoptions.prototype.level;

/**
 * interface for the minimap
 *
 * @author Andreas Bonny
 */
public interface IMinimap {

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
