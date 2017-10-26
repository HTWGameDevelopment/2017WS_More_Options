package com.moreoptions.prototype.level;

import java.util.Random;

/**
 * Implementation for a randomly generated map
 *
 * @author Andreas
 */
public class Map {

    /*
        1 --> Starting Room
        2 --> Normal Room
        3 --> Boss Room
        4 --> Vendor Room
        5 --> Item Room
        6 --> Secret Room
     */

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
        while (roomCounter <= maxRooms) {
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

        } else {
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
        if (roomCounter == 0) {
            makeRoom(x,y);
            return true;
        }

        if (!adjacentLeftEmpty(x,y) || !adjacentRightEmpty(x,y)
                || !adjacentBottomEmpty(x,y) || !adjacentTopEmpty(x,y)) {

            if (!roomIsOccupied(x,y)) {
                if (getNumberOfAdjacentRooms(x, y) < 2) {
                    makeRoom(x,y);
                    return true;
                }
            }
            return false;
        }

        return false;
    }

        /*
        1 --> Starting Room
        2 --> Normal Room
        3 --> Boss Room
        4 --> Vendor Room
        5 --> Item Room
        6 --> Secret Room
     */

    /**
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if successful; otherwise false
     */
    private boolean makeRoom(int x, int y) {
        // 1 is assigned to the starting room
        if (roomCounter == 0) {
            map[x][y] = 1;
            roomCounter++;
            return true;
        } else if (roomCounter <= maxRooms) {
            // 2 is assigned to normal rooms
            map[x][y] = 2;
            roomCounter++;
            return true;
        }
        return false;
    }

    /**
     * checks if the room on the left side of the room is empty.
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if there is an adjacent room to the left.
     */
    private boolean adjacentLeftEmpty(int x, int y) {
        if (x == 0 || map[x-1][y] == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if the room on the right side of the room is empty.
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if there is an adjacent room to the right.
     */
    private boolean adjacentRightEmpty(int x, int y) {
        if (x == width || map[x+1][y] == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if the room on the top side of the room is empty.
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if there is an adjacent room to the top.
     */
    private boolean adjacentTopEmpty(int x, int y) {
        if (y == height || map[x][y+1] == 0) {

            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if the room on the bottom side of the room is empty.
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if there is an adjacent room to the bottom.
     */
    private boolean adjacentBottomEmpty(int x, int y) {
        if (y == 0 || map[x][y-1] == 0) {

            return true;
        } else {
            return false;
        }
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
        System.out.println("");
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