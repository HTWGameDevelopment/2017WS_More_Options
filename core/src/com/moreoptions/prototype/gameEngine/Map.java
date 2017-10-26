package com.moreoptions.prototype.gameEngine;

import java.util.Random;

public class Map {
    private int[][] map;
    private int width = 11;
    private int height = 11;

    private boolean isStartingRoom = true;

    private int roomCounter = 1;
    private int maxRooms = 9;

    public Map() {
        this.map = new int[width + 1][height + 1];
        createMap();
        fillMap();

        printMap();
    }


    private void fillMap() {
        while (roomCounter <= maxRooms) {
            selectRoom();
        }
    }

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

    private boolean assignRoom(int x, int y) {
        if (roomCounter == 1) {
            makeRoom(x,y);
            return true;
        }
        if (!adjacentLeftEmpty(x,y) || !adjacentRightEmpty(x,y)
                || !adjacentBottomEmpty(x,y) || !adjacentTopEmpty(x,y)) {
            if (!roomIsOccupied(x,y)) {
                makeRoom(x,y);
                return true;
            }
            return false;
        }

        return false;
    }

    private void createMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = 0;
            }
        }
    }

    private boolean makeRoom(int x, int y) {
        if (roomCounter <= maxRooms) {
            // System.out.println("counter: " + roomCounter);
            map[x][y] = roomCounter;
            roomCounter++;
            // printMap();
            return true;
        }
        // System.out.println("limit");
        return false;
    }

    private boolean adjacentLeftEmpty(int x, int y) {
        if (map[x-1][y] == 0 || x == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean adjacentRightEmpty(int x, int y) {
        if (map[x+1][y] == 0 || x == width) {
            return true;
        } else {
            return false;
        }
    }

    private boolean adjacentTopEmpty(int x, int y) {
        if (map[x][y+1] == 0 || y == height) {

            return true;
        } else {
            return false;
        }
    }

    private boolean adjacentBottomEmpty(int x, int y) {
        if (map[x][y-1] == 0 || y == 0) {

            return true;
        } else {
            return false;
        }
    }

    private boolean roomIsOccupied(int x, int y) {
        return (map[x][y] != 0);
    }

    private void printMap() {
        System.out.println("");
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}