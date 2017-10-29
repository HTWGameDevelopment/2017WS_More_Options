package com.moreoptions.prototype.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Andreas on 28.10.2017.
 */
public class Bettermap {
    private static final int EMPTY_ROOM = 0;
    private static final int STARTING_ROOM = 1;
    private static final int NORMAL_ROOM = 2;
    private static final int BOSS_ROOM = 3;
    private static final int VENDOR_ROOM = 4;
    private static final int ITEM_ROOM = 5;
    private static final int SECRET_ROOM = 6;

    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;

    private Room[][] map;
    private List<Room> occupiedRooms;
    private List<Room> suitableSpecialRooms;

    private int width;
    private int height;

    private int maxRooms;


    // TODO: check what happens if no secret room can be assigned

    public Bettermap(int width, int height, int maxRooms) {
        this.width = width;
        this.height = height;
        this.maxRooms = maxRooms;
        this.map = new Room[width][height];
        this.occupiedRooms = new ArrayList<Room>();
        this.suitableSpecialRooms = new ArrayList<Room>();

        createMap();

        makeRooms();

        printMap();
    }

    private void makeRooms() {
        int adjacentRoomCounter;

        makeStartingRoom();

        makeNormalRooms();



        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {
                System.out.println("test");
                adjacentRoomCounter = getNumberOfAdjacentRooms(i, j);

                if (roomIsOccupied(i, j) && adjacentRoomCounter == 1) {
                    suitableSpecialRooms.add(map[i][j]);
                }
            }
        }


        makeSpecialRooms(suitableSpecialRooms.size());

    }

    private void makeSpecialRooms(int number) {
        if (number < 3) {
            new Bettermap(width, height, maxRooms);
        }

        makeBossRoom();

        makeVendorRoom();

        makeItemRoom();

        makeSecretRoom();

    }

    private void makeStartingRoom() {
        int xCoord = width/2;
        int yCoord = height/2;

        Room startingRoom = new Room(xCoord, yCoord, STARTING_ROOM);

        map[xCoord][yCoord] = startingRoom;
        map[xCoord][yCoord].setOccupied(true);

        occupiedRooms.add(startingRoom);
    }

    private void makeNormalRooms() {
        Random random = new Random();
        Room nextRoom;

        while(occupiedRooms.size() < maxRooms) {
            Room randomRoom = getRandomOccupiedRoom();

            int direction = random.nextInt(4) ;

            switch(direction) {
                case NORTH:
                    // check top bounds
                    if (randomRoom.getYCoord() == height - 1) {
                        makeNormalRooms();
                        break;
                    } else {
                        nextRoom = new Room(randomRoom.getXCoord(), randomRoom.getYCoord() + 1, NORMAL_ROOM);

                        if (nextRoom.getYCoord() > height) {
                            makeNormalRooms();
                        }
                        if (roomIsOccupied(randomRoom.getXCoord(), randomRoom.getYCoord() + 1)
                                || getNumberOfAdjacentRooms(randomRoom.getXCoord(), randomRoom.getYCoord() + 1) >= 2) {
                            break;
                        } else {
                            map[randomRoom.getXCoord()][randomRoom.getYCoord() + 1] = nextRoom;
                            occupiedRooms.add(nextRoom);

                            break;
                        }
                    }

                case EAST:
                    // check east bounds
                    if (randomRoom.getXCoord() == width - 1) {
                        makeNormalRooms();
                        break;
                    } else {
                        nextRoom = new Room(randomRoom.getXCoord() + 1, randomRoom.getYCoord(), NORMAL_ROOM);
                        if (nextRoom.getXCoord() > width) {
                            makeNormalRooms();
                        }
                        if (roomIsOccupied(randomRoom.getXCoord() + 1, randomRoom.getYCoord())
                                || getNumberOfAdjacentRooms(randomRoom.getXCoord() + 1, randomRoom.getYCoord()) >= 2) {

                            break;
                        } else {

                            map[randomRoom.getXCoord() + 1][randomRoom.getYCoord()] = nextRoom;
                            occupiedRooms.add(nextRoom);

                            break;
                        }
                    }

                case SOUTH:
                    if (randomRoom.getYCoord() == 0) {
                        makeNormalRooms();
                        break;
                    } else {
                        nextRoom = new Room(randomRoom.getXCoord(), randomRoom.getYCoord() - 1, NORMAL_ROOM);

                        if ( nextRoom.getYCoord() == 0) {
                            makeNormalRooms();
                        }

                        if (roomIsOccupied(randomRoom.getXCoord(), randomRoom.getYCoord() - 1)
                                || getNumberOfAdjacentRooms(randomRoom.getXCoord(), randomRoom.getYCoord() - 1) >= 2) {

                            break;
                        } else {

                            map[randomRoom.getXCoord()][randomRoom.getYCoord() - 1] = nextRoom;
                            occupiedRooms.add(nextRoom);

                            break;
                        }
                    }

                case WEST:
                    if (randomRoom.getXCoord() == 0) {
                        makeNormalRooms();
                        break;
                    } else {
                        nextRoom = new Room(randomRoom.getXCoord() - 1, randomRoom.getYCoord(), NORMAL_ROOM);

                        if (nextRoom.getXCoord() == 0) {
                            makeNormalRooms();
                        }

                        if (roomIsOccupied(randomRoom.getXCoord() - 1, randomRoom.getYCoord())
                                || getNumberOfAdjacentRooms(randomRoom.getXCoord() - 1, randomRoom.getYCoord()) >= 2) {

                            break;
                        } else {

                            map[randomRoom.getXCoord() - 1][randomRoom.getYCoord()] = nextRoom;
                            occupiedRooms.add(nextRoom);

                            break;
                        }
                    }
            }
        }
    }

    private void makeBossRoom() {
        Random random = new Random();
        int bossRoomIndex = random.nextInt(suitableSpecialRooms.size());

        suitableSpecialRooms.get(bossRoomIndex).setKind(BOSS_ROOM);

        suitableSpecialRooms.remove(suitableSpecialRooms.get(bossRoomIndex));

    }

    private void makeVendorRoom() {
        Random random = new Random();
        int vendorRoomIndex = random.nextInt(suitableSpecialRooms.size());

        suitableSpecialRooms.get(vendorRoomIndex).setKind(VENDOR_ROOM);

        suitableSpecialRooms.remove(suitableSpecialRooms.get(vendorRoomIndex));
    }

    private void makeItemRoom() {
        Random random = new Random();
        int itemRoomIndex = random.nextInt(suitableSpecialRooms.size());

        suitableSpecialRooms.get(itemRoomIndex).setKind(ITEM_ROOM);

        suitableSpecialRooms.remove(suitableSpecialRooms.get(itemRoomIndex));

    }

    private void makeSecretRoom() {
        Room randomRoom = getRandomOccupiedRoom();
        Random random = new Random();

        int direction = random.nextInt(4);

        switch (direction) {
            case NORTH:
                if (map[randomRoom.getXCoord()][randomRoom.getYCoord()+1].getKind() == 0) {
                    if (getNumberOfAdjacentRooms(randomRoom.getXCoord(), randomRoom.getYCoord()+1 ) > 2) {
                        map[randomRoom.getXCoord()][randomRoom.getYCoord()+1].setKind(SECRET_ROOM);
                    }
                }

                break;

            case EAST:
                if (map[randomRoom.getXCoord()+1][randomRoom.getYCoord()].getKind() == 0) {
                    if (getNumberOfAdjacentRooms(randomRoom.getXCoord()+1, randomRoom.getYCoord()) > 2) {
                        map[randomRoom.getXCoord()+1][randomRoom.getYCoord()].setKind(SECRET_ROOM);
                    }
                }
                break;

            case SOUTH:
                if (map[randomRoom.getXCoord()][randomRoom.getYCoord()-1].getKind() == 0) {
                    if (getNumberOfAdjacentRooms(randomRoom.getXCoord(), randomRoom.getYCoord()-1) > 2) {
                        map[randomRoom.getXCoord()][randomRoom.getYCoord()-1].setKind(SECRET_ROOM);
                    }
                }
                break;

            case WEST:
                if (map[randomRoom.getXCoord()-1][randomRoom.getYCoord()].getKind() == 0) {
                    if (getNumberOfAdjacentRooms(randomRoom.getXCoord()-1, randomRoom.getYCoord()) > 2) {
                        map[randomRoom.getXCoord()-1][randomRoom.getYCoord()].setKind(SECRET_ROOM);
                    }
                }
                break;
        }
    }

    private Room getRandomOccupiedRoom() {
        Random random = new Random();
        int occupiedRoomsIndex = random.nextInt(occupiedRooms.size());

        return occupiedRooms.get(occupiedRoomsIndex);
    }

    private void createMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = new Room(i,j, EMPTY_ROOM);
            }
        }
    }

    private void printMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(map[i][j].getKind() + " ");
            }
            System.out.println();
        }
    }

    private int getNumberOfAdjacentRooms(int x, int y) {
        // System.out.println("test");
        int adjacentRoomsCounter = 0;

        // check left border
        if (x == 0) {
            // check for bottom left
            if (y == 0) {
                adjacentRoomsCounter = checkRightAndTopField(x, y);
                // check for top left
            } else if (y == height - 1) {
                adjacentRoomsCounter = checkRightAndBottomField(x, y);
            } else {
                adjacentRoomsCounter = checkNotLeftSide(x, y);
            }
        }

        // check right border
        if (x == width - 1) {
            // check for bottom right
            if (y == 0) {
                adjacentRoomsCounter = checkLeftAndTopFields(x, y);
                // check for top right
            } else if (y == height - 1) {
                adjacentRoomsCounter = checkLeftAndBottomFields(x, y);
            } else {
                adjacentRoomsCounter = checkNotRightSide(x, y);
            }
        }

        // check top border
        if (y == height - 1) {
            // check for top left
            if (x == 0) {
                adjacentRoomsCounter = checkRightAndBottomField(x, y);
                // check for top right
            } else if (x == width - 1) {
                adjacentRoomsCounter = checkLeftAndBottomFields(x, y);
            } else {
                adjacentRoomsCounter = checkNotTopSide(x, y);
            }
        }

        // check bottom border
        if (y == 0) {
            // check for bottom left
            if (x == 0) {
                adjacentRoomsCounter = checkRightAndTopField(x, y);
                // check for bottom right
            } else if (x == width - 1) {
                adjacentRoomsCounter = checkLeftAndTopFields(x, y);
            } else {
                adjacentRoomsCounter = checkNotBottomSide(x, y);
            }
        }

        // check normal case
        if (x > 0 && y > 0) {
            adjacentRoomsCounter = checkAllNeighbouringFields(x, y);
        }

        return adjacentRoomsCounter;
    }

    private int checkNotBottomSide(int x, int y) {
        int adjacentRoomsCounter = 0;

        if (adjacentLeftOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentRightOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentTopOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        return adjacentRoomsCounter;
    }

    private int checkNotTopSide(int x, int y) {
        int adjacentRoomsCounter = 0;

        if (adjacentBottomOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentRightOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentLeftOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        return adjacentRoomsCounter;
    }

    private int checkNotRightSide(int x, int y) {
        int adjacentRoomsCounter = 0;

        if (adjacentLeftOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentTopOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentBottomOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        return adjacentRoomsCounter;
    }

    private int checkLeftAndBottomFields(int x, int y) {
        int adjacentRoomsCounter = 0;

        if (adjacentLeftOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentBottomOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        return adjacentRoomsCounter;
    }

    private int checkLeftAndTopFields(int x, int y) {
        int adjacentRoomsCounter = 0;

        if (adjacentLeftOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentTopOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        return adjacentRoomsCounter;
    }

    private int checkNotLeftSide(int x, int y) {
        int adjacentRoomsCounter = 0;

        if (adjacentBottomOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentTopOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentRightOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        return adjacentRoomsCounter;
    }

    private int checkRightAndBottomField(int x, int y) {
        int adjacentRoomsCounter = 0;

        if (adjacentRightOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentBottomOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        return adjacentRoomsCounter;
    }

    private int checkRightAndTopField(int x, int y) {
        int adjacentRoomsCounter = 0;

        if (adjacentRightOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentTopOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        return adjacentRoomsCounter;
    }

    private int checkAllNeighbouringFields(int x, int y) {
        int adjacentRoomsCounter = 0;

        if (adjacentBottomOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentLeftOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentTopOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentRightOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        return adjacentRoomsCounter;

    }

    private boolean adjacentLeftOccupied(int x, int y) {
        if (x == 0) {
            return false;
        }
        return (map[x-1][y].getKind() > 0);
    }

    private boolean adjacentRightOccupied(int x, int y) {
        if (x == width - 1) {
            return false;
        }
        return (map[x+1][y].getKind() > 0);
    }

    private boolean adjacentTopOccupied(int x, int y) {
        if (y == height - 1) {
            return false;
        }
        return (map[x][y+1].getKind() > 0);
    }

    private boolean adjacentBottomOccupied(int x, int y) {
        if (y == 0) {
            return false;
        }
        return (map[x][y-1].getKind() > 0);
    }

    private boolean roomIsOccupied(int x, int y) {
        return (map[x][y].getKind() != 0);
    }
}
