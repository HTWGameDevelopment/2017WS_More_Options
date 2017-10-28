package com.moreoptions.prototype.level;

import java.util.Random;

/**
 * Implementation for a randomly generated map
 *
 * @author Andreas
 */

// TODO: special rooms should not be allowed to be next to each other (except secret room & starting room)
public class Map {
    private static final int STARTING_ROOM = 1;
    private static final int NORMAL_ROOM = 2;
    private static final int BOSS_ROOM = 3;
    private static final int VENDOR_ROOM = 4;
    private static final int ITEM_ROOM = 5;
    private static final int SECRET_ROOM = 6;

    private int[][] map;
    private int width = 11;
    private int height = 11;

    private boolean isStartingRoom = true;


    private int roomCounter = 0;

    private int maxRooms = 15;

    public Map() {
        this.map = new int[width + 1][height + 1];
        createMap();
        fillMap();

        printMap();
    }

    /**
     * gets the number of adjacent rooms for a room.
     * @param x x-coordinate of the room.
     * @param y y-coordinate of the room.
     * @return number of adjacent rooms.
     */
    private int getNumberOfAdjacentRooms(int x, int y) {
        int adjacentRoomsCounter = 0;

        if (!adjacentLeftEmpty(x,y)) {
            adjacentRoomsCounter++;
        }
        if (!adjacentRightEmpty(x,y)) {
            adjacentRoomsCounter++;
        }
        if (!adjacentTopEmpty(x,y)) {
            adjacentRoomsCounter++;
        }
        if (!adjacentBottomEmpty(x,y)) {
            adjacentRoomsCounter++;
        }

        return adjacentRoomsCounter;
    }

    /**
     * fills the map with a number of rooms.
     */
    private void fillMap() {
        // TODO: better closure for while loop (not maxRooms + X)
        while (roomCounter <= maxRooms + 4) {
            selectRoom();
        }
    }

    /**
     * selects a random room in the map.
     * starts with a starting room in the middle of the map.
     * @return true if successful.
     */
    private boolean selectRoom() {
        Random random = new Random();
        int xCord;
        int yCord;

        if (isStartingRoom) {
            xCord = width / 2;
            yCord = height / 2;
            isStartingRoom = false;

            assignRoom(xCord, yCord);

        } else if (roomCounter <= maxRooms+4) {
            xCord = random.nextInt(width - 1) + 1;
            yCord = random.nextInt(height - 1) + 1;

            assignRoom(xCord, yCord);
        }
        return true;
    }

    /**
     * assigns a room to a point inside the map.
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if successful.
     */
    private boolean assignRoom(int x, int y) {
        // starting room
        if (roomCounter == 0) {
            makeRoom(x,y, STARTING_ROOM);
            return true;
        }

        // normal rooms
        if (roomCounter <= maxRooms) {
            if (!adjacentLeftEmpty(x, y) || !adjacentRightEmpty(x, y)
                    || !adjacentBottomEmpty(x, y) || !adjacentTopEmpty(x, y)) {

                if (!roomIsOccupied(x, y)) {
                    if (getNumberOfAdjacentRooms(x, y) < 2) {
                        makeRoom(x, y, NORMAL_ROOM);
                        return true;
                    }
                }
                return false;
            }
        }
        // TODO: make this better

        // boss room
        if (roomCounter == maxRooms+1) {
            if (!roomIsOccupied(x, y)) {
                if (getNumberOfAdjacentRooms(x, y) == 1) {
                    makeRoom(x, y, BOSS_ROOM);

                    return true;
                }
            }
        }

        // vendor room
        if (roomCounter == maxRooms+2) {
            if (!roomIsOccupied(x, y)) {
                if (getNumberOfAdjacentRooms(x, y) == 1) {
                    makeRoom(x, y, VENDOR_ROOM);

                    return true;
                }
            }
        }

        // item room
        if (roomCounter == maxRooms+3) {
            if (!roomIsOccupied(x, y)) {
                if (getNumberOfAdjacentRooms(x, y) == 1) {
                    makeRoom(x, y, ITEM_ROOM);

                    return true;
                }
            }
        }

        // secret room
        if (roomCounter == maxRooms+4) {
            if (!roomIsOccupied(x, y)) {
                if (getNumberOfAdjacentRooms(x, y) >= 2) {
                    makeRoom(x, y, SECRET_ROOM);

                    return true;
                }
            }
        }


        return false;
    }

    /**
     * assigns a number to a room according to it's purpose
     * starting room    --> 1
     * normal room      --> 2
     * boss room        --> 3
     * vendor room      --> 4
     * item room        --> 5
     * secret room      --> 6
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if successful; otherwise false
     */
    private boolean makeRoom(int x, int y, int roomNumberCommand) {
        map[x][y] = roomNumberCommand;
        if (roomCounter <= maxRooms + 4) {
            roomCounter++;
        }

        return true;
    }

    /**
     * checks if the room on the left side of the room is empty.
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if there is an adjacent room to the left.
     */
    private boolean adjacentLeftEmpty(int x, int y) {
        return (x == 0 || map[x-1][y] == 0);
    }

    /**
     * checks if the room on the right side of the room is empty.
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if there is an adjacent room to the right.
     */
    private boolean adjacentRightEmpty(int x, int y) {
        return (x == width || map[x+1][y] == 0);
    }

    /**
     * checks if the room on the top side of the room is empty.
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if there is an adjacent room to the top.
     */
    private boolean adjacentTopEmpty(int x, int y) {
        return (y == height || map[x][y+1] == 0);
    }

    /**
     * checks if the room on the bottom side of the room is empty.
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if there is an adjacent room to the bottom.
     */
    private boolean adjacentBottomEmpty(int x, int y) {
        return (y == 0 || map[x][y-1] == 0);
    }

    /**
     * checks if the room is already occupied.
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true/false
     */
    private boolean roomIsOccupied(int x, int y) {
        return (map[x][y] != 0);
    }

    /**
     * fills an array with "empty" rooms.
     */
    private void createMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = 0;
            }
        }
    }

    /**
     * prints the map to the shell
     */
    private void printMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j] == 0) {
                    System.out.print("  ");
                } else {
                    System.out.print(map[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}