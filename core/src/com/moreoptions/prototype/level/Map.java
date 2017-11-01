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
    private List<Room> possibleSecretRooms;
    private List<Room> possibleSecretRoomsSecondary;

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
        this.possibleSecretRooms = new ArrayList<Room>();
        this.possibleSecretRoomsSecondary = new ArrayList<Room>();

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

        makeBossRoom();

        countNeighbours();

        makeItemRoom();

        countNeighbours();

        makeVendorRoom();

        countNeighbours();
        getPossibleSecretRooms();

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

        while(no boss room)
            get random occupied room
            get random direction

            switch(direction)
                case
                    check if room to be would be outside the bounds
                    check if room to be only has 1 neighbour
                    check if room to be is unoccupied
                    make the room
         */

        Random random = new Random();
        while (!containsBossRoom) {
            int roomIndex = random.nextInt(existingRooms.size());

            int direction = random.nextInt(4);

            Room randomRoom = existingRooms.get(roomIndex);

            switch (direction) {
                case NORTH:
                    // if inside the map
                    if (!checkNorthOutside(randomRoom.getYCoord())) {
                        // if room to be has 1 neighbour
                        if (map[randomRoom.getXCoord()][randomRoom.getYCoord() + 1].getNeighbours() == 1) {
                            // if room to be is still empty
                            if (map[randomRoom.getXCoord()][randomRoom.getYCoord() + 1].getKind() == 0) {
                                // make the room
                                makeRoomNorth(randomRoom.getXCoord(), randomRoom.getYCoord(), BOSS_ROOM);
                                containsBossRoom = true;
                            }
                        }
                    }
                    break;

                case EAST:
                    if (!checkEastOutside(randomRoom.getXCoord())) {
                        if (map[randomRoom.getXCoord() + 1][randomRoom.getYCoord()].getNeighbours() == 1) {
                            if (map[randomRoom.getXCoord() + 1][randomRoom.getYCoord()].getKind() == 0) {
                                makeRoomEast(randomRoom.getXCoord(), randomRoom.getYCoord(), BOSS_ROOM);
                                containsBossRoom = true;
                            }
                        }
                    }
                    break;

                case SOUTH:
                    if (!checkSouthOutside(randomRoom.getYCoord())) {
                        if (map[randomRoom.getXCoord()][randomRoom.getYCoord() - 1].getNeighbours() == 1) {
                            if (map[randomRoom.getXCoord()][randomRoom.getYCoord() - 1].getKind() == 0) {
                                makeRoomSouth(randomRoom.getXCoord(), randomRoom.getYCoord(), BOSS_ROOM);
                                containsBossRoom = true;
                            }
                        }
                    }
                    break;

                case WEST:
                    if (! checkWestOutside(randomRoom.getXCoord())) {
                        if (map[randomRoom.getXCoord() - 1][randomRoom.getYCoord()].getNeighbours() == 1) {
                            if (map[randomRoom.getXCoord() - 1][randomRoom.getYCoord()].getKind() == 0) {
                                makeRoomWest(randomRoom.getXCoord(), randomRoom.getYCoord(), BOSS_ROOM);
                                containsBossRoom = true;
                            }
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void makeVendorRoom() {

        Random random = new Random();
        while (!containsVendorRoom) {
            int roomIndex = random.nextInt(existingRooms.size());

            int direction = random.nextInt(4);

            Room randomRoom = existingRooms.get(roomIndex);

            switch (direction) {
                case NORTH:
                    // if inside the map
                    if (!checkNorthOutside(randomRoom.getYCoord())) {
                        // if room to be has 1 neighbour
                        if (map[randomRoom.getXCoord()][randomRoom.getYCoord() + 1].getNeighbours() == 1) {
                            // if room to be is still empty
                            if (map[randomRoom.getXCoord()][randomRoom.getYCoord() + 1].getKind() == 0) {
                                // make the room
                                makeRoomNorth(randomRoom.getXCoord(), randomRoom.getYCoord(), VENDOR_ROOM);
                                containsVendorRoom = true;
                            }
                        }
                    }
                    break;

                case EAST:
                    if (!checkEastOutside(randomRoom.getXCoord())) {
                        if (map[randomRoom.getXCoord() + 1][randomRoom.getYCoord()].getNeighbours() == 1) {
                            if (map[randomRoom.getXCoord() + 1][randomRoom.getYCoord()].getKind() == 0) {
                                makeRoomEast(randomRoom.getXCoord(), randomRoom.getYCoord(), VENDOR_ROOM);
                                containsVendorRoom = true;
                            }
                        }
                    }
                    break;

                case SOUTH:
                    if (!checkSouthOutside(randomRoom.getYCoord())) {
                        if (map[randomRoom.getXCoord()][randomRoom.getYCoord() - 1].getNeighbours() == 1) {
                            if (map[randomRoom.getXCoord()][randomRoom.getYCoord() - 1].getKind() == 0) {
                                makeRoomSouth(randomRoom.getXCoord(), randomRoom.getYCoord(), VENDOR_ROOM);
                                containsVendorRoom = true;
                            }
                        }
                    }
                    break;

                case WEST:
                    if (! checkWestOutside(randomRoom.getXCoord())) {
                        if (map[randomRoom.getXCoord() - 1][randomRoom.getYCoord()].getNeighbours() == 1) {
                            if (map[randomRoom.getXCoord() - 1][randomRoom.getYCoord()].getKind() == 0) {
                                makeRoomWest(randomRoom.getXCoord(), randomRoom.getYCoord(), VENDOR_ROOM);
                                containsVendorRoom = true;
                            }
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void makeItemRoom() {
        Random random = new Random();
        while (!containsItemRoom) {
            int roomIndex = random.nextInt(existingRooms.size());

            int direction = random.nextInt(4);

            Room randomRoom = existingRooms.get(roomIndex);

            switch (direction) {
                case NORTH:
                    if (!checkNorthOutside(randomRoom.getYCoord())) {
                        if (map[randomRoom.getXCoord()][randomRoom.getYCoord() + 1].getNeighbours() == 1) {
                            if (map[randomRoom.getXCoord()][randomRoom.getYCoord() + 1].getKind() == 0) {
                                makeRoomNorth(randomRoom.getXCoord(), randomRoom.getYCoord(), ITEM_ROOM);
                                containsItemRoom = true;
                            }
                        }
                    }
                    break;

                case EAST:
                    if (!checkEastOutside(randomRoom.getXCoord())) {
                        if (map[randomRoom.getXCoord() + 1][randomRoom.getYCoord()].getNeighbours() == 1) {
                            if (map[randomRoom.getXCoord() + 1][randomRoom.getYCoord()].getKind() == 0) {
                                makeRoomEast(randomRoom.getXCoord(), randomRoom.getYCoord(), ITEM_ROOM);
                                containsItemRoom = true;
                            }
                        }
                    }
                    break;

                case SOUTH:
                    if (!checkSouthOutside(randomRoom.getYCoord())) {
                        if (map[randomRoom.getXCoord()][randomRoom.getYCoord() - 1].getNeighbours() == 1) {
                            if (map[randomRoom.getXCoord()][randomRoom.getYCoord() - 1].getKind() == 0) {
                                makeRoomSouth(randomRoom.getXCoord(), randomRoom.getYCoord(), ITEM_ROOM);
                                containsItemRoom = true;
                            }
                        }
                    }
                    break;

                case WEST:
                    if (! checkWestOutside(randomRoom.getXCoord())) {
                        if (map[randomRoom.getXCoord() - 1][randomRoom.getYCoord()].getNeighbours() == 1) {
                            if (map[randomRoom.getXCoord() - 1][randomRoom.getYCoord()].getKind() == 0) {
                                makeRoomWest(randomRoom.getXCoord(), randomRoom.getYCoord(), ITEM_ROOM);
                                containsItemRoom = true;
                            }
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void makeSecretRoom() {
        Random random = new Random();
        int tries = 0;


            while (!containsSecretRoom) {
                if (possibleSecretRooms.size() != 0) {
                    int secretRoomIndex = random.nextInt(possibleSecretRooms.size());
                    Room secretRoom = possibleSecretRooms.get(secretRoomIndex);

                    map[secretRoom.getXCoord()][secretRoom.getYCoord()].setKind(SECRET_ROOM);
                    existingRooms.add(secretRoom);
                    containsSecretRoom = true;

                } else if (possibleSecretRooms.size() == 0) {
                    getSecretRoomWithTwoNeighbours();
                    int secretRoomIndex = random.nextInt(possibleSecretRoomsSecondary.size());
                    Room secretRoom = possibleSecretRoomsSecondary.get(secretRoomIndex);
                    map[secretRoom.getXCoord()][secretRoom.getYCoord()].setKind(SECRET_ROOM);
                    existingRooms.add(secretRoom);
                    containsSecretRoom = true;

                }  else {
                    System.out.println("couldn't find suitable secret room --> won't be generated");
                    containsSecretRoom = true;
                }
            }
    }

    @Override
    public void printMap() {
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
        if (!checkNorthOutside(y)) {
            if (map[x][y + 1].getKind() == 0) {
                room = new Room(x, y + 1, kind);
                map[x][y + 1] = room;
                existingRooms.add(room);
            }
        }
    }

    @Override
    public void makeRoomEast(int x, int y, int kind) {
        Room room;
        if (!checkEastOutside(x)) {
            if (map[x + 1][y].getKind() == 0) {
                room = new Room(x + 1, y, kind);
                map[x + 1][y] = room;
                existingRooms.add(room);
            }
        }
    }

    @Override
    public void makeRoomSouth(int x, int y, int kind) {
        Room room;
        if (!checkSouthOutside(y)) {
            if (map[x][y - 1].getKind() == 0) {
                room = new Room(x, y - 1, kind);
                map[x][y - 1] = room;
                existingRooms.add(room);
            }
        }
    }

    @Override
    public void makeRoomWest(int x, int y, int kind) {
        Room room;
        if (!checkWestOutside(x)) {
            if (map[x - 1][y].getKind() == 0) {
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

    @Override
    public void getPossibleSecretRooms() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (map[x][y].getKind() == 0) {
                    if (map[x][y].getNeighbours() > 2) {
                        possibleSecretRooms.add(map[x][y]);
                    }
                }
            }
        }
    }

    private void getSecretRoomWithTwoNeighbours() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (map[x][y].getKind() == 0) {
                    if (map[x][y].getNeighbours() == 2) {
                        possibleSecretRoomsSecondary.add(map[x][y]);
                    }
                }
            }
        }
    }
}
