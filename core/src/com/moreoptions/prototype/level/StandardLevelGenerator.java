package com.moreoptions.prototype.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by denwe on 04.11.2017.
 */
public class StandardLevelGenerator implements LevelGenerator {

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

    private RoomBlueprint[][] map;

    private List<RoomBlueprint> existingRooms;
    private List<RoomBlueprint> possibleSecretRooms;
    private List<RoomBlueprint> possibleSecretRoomsSecondary;

    private int maxRooms;

    private boolean containsBossRoom = false;
    private boolean containsItemRoom = false;
    private boolean containsVendorRoom = false;
    private boolean containsSecretRoom = false;


    /**
     * fills the map with "empty" rooms which will not be accessible by the player
     */

    private void createMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = new RoomBlueprint(x, y, EMPTY_ROOM);
            }
        }
    }

    /**
     * calls the methods responsible for generating the different rooms in each minimap
     */

    private RoomBlueprint[][] makeRooms() {
        makeStartingRoom();

        makeNormalRooms();

        makeSpecialRooms();
        return map;
    }

    /**
     * calls the methods which create the special rooms
     *
     * in between each call is a method that counts the amount of neighbours each room has in order so that
     * a special room is only accessible by one side
     */

    private void makeSpecialRooms() {
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

    /**
     * creates a predetermined amount of normal rooms
     */

    private void makeNormalRooms() {
        Random random = new Random();
        int roomIndex;
        int direction;
        RoomBlueprint randomRoom;

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

    /**
     * creates the room in which the player will be placed upon start of each level
     */

    private void makeStartingRoom() {
        int x = width / 2;
        int y = height / 2;

        RoomBlueprint startingRoom = new RoomBlueprint(x, y, STARTING_ROOM);
        map[x][y] = startingRoom;
        existingRooms.add(startingRoom);
    }

    /**
     * creates the room in which the boss of each level will be placed
     */

    private void makeBossRoom() {

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

            RoomBlueprint randomRoom = existingRooms.get(roomIndex);

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

    /**
     * creates the room in which the player can spend resources in order to gain benefits
     */

    private void makeVendorRoom() {

        Random random = new Random();
        while (!containsVendorRoom) {
            int roomIndex = random.nextInt(existingRooms.size());

            int direction = random.nextInt(4);

            RoomBlueprint randomRoom = existingRooms.get(roomIndex);

            switch (direction) {
                case NORTH:
                    if (!checkNorthOutside(randomRoom.getYCoord())) {
                        if (map[randomRoom.getXCoord()][randomRoom.getYCoord() + 1].getNeighbours() == 1) {
                            if (map[randomRoom.getXCoord()][randomRoom.getYCoord() + 1].getKind() == 0) {
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

    /**
     * creates a room in which an item or some other benefit will be spawned
     */

    private void makeItemRoom() {
        Random random = new Random();
        while (!containsItemRoom) {
            int roomIndex = random.nextInt(existingRooms.size());

            int direction = random.nextInt(4);

            RoomBlueprint randomRoom = existingRooms.get(roomIndex);

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

    /**
     * creates a room which has multiple neighbours which will not be visible without some special means
     */

    private void makeSecretRoom() {
        Random random = new Random();

        while (!containsSecretRoom) {
            if (possibleSecretRooms.size() != 0) {
                int secretRoomIndex = random.nextInt(possibleSecretRooms.size());
                RoomBlueprint secretRoom = possibleSecretRooms.get(secretRoomIndex);

                map[secretRoom.getXCoord()][secretRoom.getYCoord()].setKind(SECRET_ROOM);
                existingRooms.add(secretRoom);
                containsSecretRoom = true;

            } else if (possibleSecretRooms.size() == 0) {
                getSecretRoomWithTwoNeighbours();

                if (possibleSecretRoomsSecondary.size() != 0) {
                    int secretRoomIndex = random.nextInt(possibleSecretRoomsSecondary.size());
                    RoomBlueprint secretRoom = possibleSecretRoomsSecondary.get(secretRoomIndex);
                    map[secretRoom.getXCoord()][secretRoom.getYCoord()].setKind(SECRET_ROOM);
                    existingRooms.add(secretRoom);
                    containsSecretRoom = true;
                }
            }  else {
                System.out.println("couldn't find suitable secret room --> won't be generated");
                containsSecretRoom = true;
            }
        }
    }

    /**
     * prints the map to the terminal
     */

    public void printMap() {
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                System.out.print(" " + map[x][y].getKind() + " ");
            }
            System.out.println();
        }
    }

    /**
     * creates a room north from a position on the map
     * @param x x-coordinate from the room the new room will originate
     * @param y y-coordinate from the room the new room will originate
     * @param kind the kind of room that will be generated
     */

    private void makeRoomNorth(int x, int y, int kind) {
        RoomBlueprint room;
        if (!checkNorthOutside(y)) {
            if (map[x][y + 1].getKind() == 0) {
                room = new RoomBlueprint(x, y + 1, kind);
                map[x][y + 1] = room;
                existingRooms.add(room);
            }
        }
    }

    /**
     * creates a room east from a position on the map
     * @param x x-coordinate from the room the new room will originate
     * @param y y-coordinate from the room the new room will originate
     * @param kind the kind of room that will be generated
     */

    private void makeRoomEast(int x, int y, int kind) {
        RoomBlueprint room;
        if (!checkEastOutside(x)) {
            if (map[x + 1][y].getKind() == 0) {
                room = new RoomBlueprint(x + 1, y, kind);
                map[x + 1][y] = room;
                existingRooms.add(room);
            }
        }
    }

    /**
     * creates a room south from a position on the map
     * @param x x-coordinate from the room the new room will originate
     * @param y y-coordinate from the room the new room will originate
     * @param kind the kind of room that will be generated
     */

    private void makeRoomSouth(int x, int y, int kind) {
        RoomBlueprint room;
        if (!checkSouthOutside(y)) {
            if (map[x][y - 1].getKind() == 0) {
                room = new RoomBlueprint(x, y - 1, kind);
                map[x][y - 1] = room;
                existingRooms.add(room);
            }
        }
    }

    /**
     * creates a room west from a position on the map
     * @param x x-coordinate from the room the new room will originate
     * @param y y-coordinate from the room the new room will originate
     * @param kind the kind of room that will be generated
     */

    private void makeRoomWest(int x, int y, int kind) {
        RoomBlueprint room;
        if (!checkWestOutside(x)) {
            if (map[x - 1][y].getKind() == 0) {
                room = new RoomBlueprint(x - 1, y, kind);
                map[x - 1][y] = room;
                existingRooms.add(room);
            }
        }
    }

    /**
     * checks if the room is at the northern bound of the map
     * @param y y-coordinate of the room to be checked
     * @return true if it is at the northern bound
     */

    private boolean checkNorthOutside(int y) {
        return (y == height - 1);
    }

    /**
     * checks if the room is at the eastern bound of the map
     * @param x x-coordinate of the room to be checked
     * @return true if it is at the eastern bound
     */

    private boolean checkEastOutside(int x) {
        return (x == width - 1);
    }

    /**
     * checks if the room is at the southern bound of the map
     * @param y y-coordinate of the room to be checked
     * @return true if it is at the southern bound
     */

    private boolean checkSouthOutside(int y) {
        return (y == 0);
    }

    /**
     * checks if the room is at the western bound of the map
     * @param x x-coordinate of the room to be checked
     * @return true if it is at the western bound
     */
    private boolean checkWestOutside(int x) {
        return (x == 0);
    }

    /**
     * counts the amount of neighbours a room has and sets that amount of rooms
     * in the room class
     */

    private void countNeighbours() {
        int neighbours = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // four directions
                // north
                if (!checkNorthOutside(y)) {
                    if (map[x][y + 1].getKind() != 0) {
                        neighbours++;
                        map[x][y + 1].setHasNeighbourTop(true);
                    }
                }

                // east
                if (!checkEastOutside(x)) {
                    if (map[x + 1][y].getKind() != 0) {
                        neighbours++;
                        map[x + 1][y].setHasNeighbourRight(true);
                    }
                }

                // south
                if (!checkSouthOutside(y)) {
                    if (map[x][y - 1].getKind() != 0) {
                        neighbours++;
                        map[x][y - 1].setHasNeighbourBottom(true);
                    }
                }


                // west
                if (!checkWestOutside(x)) {
                    if (map[x - 1][y].getKind() != 0) {
                        neighbours++;
                        map[x - 1][y].setHasNeighbourLeft(true);
                    }
                }

                // setNeighbours
                map[x][y].setNeighbours(neighbours);
                neighbours = 0;
            }
        }
    }

    /**
     * gets the best suitable empty rooms for the generation of the secret room (3+ neighbours)
     */

    private void getPossibleSecretRooms() {
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

    /**
     * gets the second best suitable empty rooms for the generation of the secret room (2 neighbours)
     */
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

    @Override
    public Level getLevel(int width, int height, int roomCount) {
        this.width = width;
        this.height = height;
        this.maxRooms = roomCount;

        this.map = new RoomBlueprint[width][height];
        this.existingRooms = new ArrayList<RoomBlueprint>();
        this.possibleSecretRooms = new ArrayList<RoomBlueprint>();
        this.possibleSecretRoomsSecondary = new ArrayList<RoomBlueprint>();

        createMap();

        RoomBlueprint[][] rooms = makeRooms();

        LevelBlueprint level = new LevelBlueprint(rooms,width,height);

        return level.generateLevel();
    }
}
