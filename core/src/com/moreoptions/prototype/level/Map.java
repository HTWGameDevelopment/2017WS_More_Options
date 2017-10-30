package com.moreoptions.prototype.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation for a randomly generated map
 *
 * @author Andreas
 */

public class Map implements ILevel{

    private static final int EMPTY_ROOM = 0;
    private static final int STARTING_ROOM = 1;
    private static final int NORMAL_ROOM = 2; 
    private static final int BOSS_ROOM = 3; 
    private static final int ITEM_ROOM = 4; 
    private static final int VENDOR_ROOM = 5; 
    private static final int SECRET_ROOM = 6;

    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;

    private int width;
    private int height;

    private Room[][] map;

    private List<Room> existingRooms;
    private List<Room> singleNeighbourRooms;

    private int maxRooms;

    private boolean containsBossRoom = false;
    private boolean containsItemRoom = false;
    private boolean containsVendorRoom = false;
    private boolean containsSecretRoom = false;

    public Map(int width, int height, int maxRooms) {
        this.width = width;
        this.height = height;

        this.map = new Room[width][height];
        this.existingRooms = new ArrayList<Room>();
        this.singleNeighbourRooms = new ArrayList<Room>();

        this.maxRooms = maxRooms;

        createMap();

        makeRooms();

        printMap();
    }

    @Override
    public void createMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = new Room(x, y, EMPTY_ROOM);
            }
        }
    }

    @Override
    public void makeRooms() {
        makeStartingRoom();

        makeNormalRooms();

        makeSpecialRooms();
    }

    @Override
    public void makeSpecialRooms() {
        countNeighbours();
        getRoomsWithOneNeighbour();

        makeBossRoom();

        countNeighbours();
        getRoomsWithOneNeighbour();

        makeItemRoom();

        countNeighbours();
        getRoomsWithOneNeighbour();

        makeVendorRoom();

        countNeighbours();
        getRoomsWithOneNeighbour();

        makeSecretRoom();
    }

    @Override
    public void makeNormalRooms() {
        Random random = new Random();
        int roomIndex;
        int direction;
        Room randomRoom;

        while (existingRooms.size() < maxRooms) {
            roomIndex = random.nextInt(existingRooms.size());
            randomRoom = existingRooms.get(roomIndex);

            direction = random.nextInt(4);

            switch (direction) {
                case NORTH:
                    makeRoomNorth(randomRoom.getXCoord(), randomRoom.getYCoord(), NORMAL_ROOM);
                    break;

                case EAST:
                    makeRoomEast(randomRoom.getXCoord(), randomRoom.getYCoord(), NORMAL_ROOM);
                    break;

                case SOUTH:
                    makeRoomSouth(randomRoom.getXCoord(), randomRoom.getYCoord(), NORMAL_ROOM);
                    break;

                case WEST:
                    makeRoomWest(randomRoom.getXCoord(), randomRoom.getYCoord(), NORMAL_ROOM);
                    break;
            }
        }
    }

    @Override
    public void makeStartingRoom() {
        int x = width / 2;
        int y = height / 2;

        Room startingRoom = new Room(x, y, STARTING_ROOM);
        map[x][y] = startingRoom;
        existingRooms.add(startingRoom);
    }

    @Override
    public void makeBossRoom() {
        /*
        get all rooms with one neighbour
        get a random room from that list
        get a random direction

        try to make the room
        while (false) do
            go direction
            check for bounds, occupied and neighbours
            make room
            add room to lists and map
         */

        Random random = new Random();
        while (!containsBossRoom) {
            int roomIndex = random.nextInt(singleNeighbourRooms.size());

            int direction = random.nextInt(4);
            Room randomRoom = singleNeighbourRooms.get(roomIndex);

            switch (direction) {
                case NORTH:
                    if (checkNorthOutside(randomRoom.getYCoord())) {
                        break;
                    } else if (map[randomRoom.getXCoord()][randomRoom.getYCoord() + 1].getKind() != 0) {
                        break;
                    } else {
                        makeRoomNorth(randomRoom.getXCoord(), randomRoom.getYCoord() + 1, BOSS_ROOM);
                        containsBossRoom = true;
                    }
                    break;

                case EAST:
                    if (checkEastOutside(randomRoom.getXCoord())) {
                        break;
                    } else if (map[randomRoom.getXCoord() + 1][randomRoom.getYCoord()].getKind() != 0) {
                        break;
                    } else {
                        makeRoomEast(randomRoom.getXCoord() + 1, randomRoom.getYCoord(), BOSS_ROOM);
                        containsBossRoom = true;
                    }
                    break;

                case SOUTH:
                    if (checkSouthOutside(randomRoom.getYCoord())) {
                        break;
                    } else if (map[randomRoom.getXCoord()][randomRoom.getYCoord() - 1].getKind() != 0) {
                        break;
                    } else {
                        makeRoomSouth(randomRoom.getXCoord(), randomRoom.getYCoord() - 1, BOSS_ROOM);
                        containsBossRoom = true;
                    }
                    break;

                case WEST:
                    if (checkWestOutside(randomRoom.getXCoord())) {
                        break;
                    } else if (map[randomRoom.getXCoord() - 1][randomRoom.getYCoord()].getKind() != 0) {
                        break;
                    } else {
                        makeRoomWest(randomRoom.getXCoord() - 1, randomRoom.getYCoord(), BOSS_ROOM);
                        containsBossRoom = true;
                    }
                    break;
            }
        }
    }

    @Override
    public void makeVendorRoom() {

    }

    @Override
    public void makeItemRoom() {

    }

    @Override
    public void makeSecretRoom() {

    }

    @Override
    public void printMap() {
        int counter = 0;

        countNeighbours();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.println("index: " + counter + ", X: " + i + ", Y: " + j + ", neighbours: " + map[i][j].getNeighbours() + ", kind: " + map[i][j].getKind());
                counter++;
            }
        }

        getRoomsWithOneNeighbour();

        System.out.println("size: " + singleNeighbourRooms.size());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                System.out.print(" " + map[x][y].getKind() + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void makeRoomNorth(int x, int y, int kind) {
        Room room;
        if (checkNorthOutside(y)) {
            // do nothing
        } else {
            if (map[x][y + 1].getKind() != 0) {
                // do nothing
            } else {
                room = new Room(x, y + 1, kind);
                map[x][y + 1] = room;
                existingRooms.add(room);
            }
        }
    }

    @Override
    public void makeRoomEast(int x, int y, int kind) {
        Room room;
        if (checkEastOutside(x)) {
            // do nothing
        } else {
            if (map[x + 1][y].getKind() != 0) {
                // do nothing
            } else {
                room = new Room(x + 1, y, kind);
                map[x + 1][y] = room;
                existingRooms.add(room);
            }
        }
    }

    @Override
    public void makeRoomSouth(int x, int y, int kind) {
        Room room;
        if (checkSouthOutside(y)) {
            // do nothing
        } else {
            if (map[x][y - 1].getKind() != 0) {
                // do nothing
            } else {
                room = new Room(x, y - 1, kind);
                map[x][y - 1] = room;
                existingRooms.add(room);
            }
        }
    }

    @Override
    public void makeRoomWest(int x, int y, int kind) {
        Room room;
        if (checkWestOutside(x)) {
            // do nothing
        } else {
            if (map[x - 1][y].getKind() != 0) {
                // do nothing
            } else {
                room = new Room(x - 1, y, kind);
                map[x - 1][y] = room;
                existingRooms.add(room);
            }
        }
    }

    @Override
    public boolean checkNorthOutside(int y) {
        return (y == height - 1);
    }

    @Override
    public boolean checkEastOutside(int x) {
        return (x == width - 1);
    }

    @Override
    public boolean checkSouthOutside(int y) {
        return (y == 0);
    }

    @Override
    public boolean checkWestOutside(int x) {
        return (x == 0);
    }

    @Override
    public void getRoomsWithOneNeighbour() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (map[x][y].getKind() != 0) {
                    if (map[x][y].getNeighbours() == 1) {
                        singleNeighbourRooms.add(map[x][y]);
                    }
                }
            }
        }
    }

    @Override
    public void countNeighbours() {
        int neighbours = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // four directions
                // north
                if (!checkNorthOutside(y)) {
                    if (map[x][y + 1].getKind() != 0) {
                        neighbours++;
                    }
                }

                // east
                if (!checkEastOutside(x)) {
                    if (map[x + 1][y].getKind() != 0) {
                        neighbours++;
                    }
                }

                // south
                if (!checkSouthOutside(y)) {
                    if (map[x][y - 1].getKind() != 0) {
                        neighbours++;
                    }
                }


                // west
                if (!checkWestOutside(x)) {
                    if (map[x - 1][y].getKind() != 0) {
                        neighbours++;
                    }
                }

                // setNeighbours
                map[x][y].setNeighbours(neighbours);
                neighbours = 0;
            }
        }
    }
}
