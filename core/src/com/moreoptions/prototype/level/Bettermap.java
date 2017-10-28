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

    private int width;
    private int height;

    private int maxRooms;

    private boolean containsBossRoom = false;
    private boolean containsVendorRoom = false;
    private boolean containsItemRoom = false;
    private boolean containsSecretRoom = false;

    // TODO: check bounds

    public Bettermap(int width, int height, int maxRooms) {
        this.width = width;
        this.height = height;
        this.maxRooms = maxRooms;
        this.map = new Room[width][height];
        this.occupiedRooms = new ArrayList<Room>();

        createMap();

        makeRooms();

        printMap();
    }

    private void makeRooms() {
        makeStartingRoom();

        makeNormalRooms();

        while(!containsBossRoom) {
            makeBossRoom();
        }

        while(!containsVendorRoom) {
            makeVendorRoom();
        }

        while(!containsItemRoom) {
            makeItemRoom();
        }

        while(!containsSecretRoom) {
            makeSecretRoom();
        }
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
                    nextRoom = new Room(randomRoom.getXCoord(), randomRoom.getYCoord()+1, NORMAL_ROOM);

                    if (roomIsOccupied(randomRoom.getXCoord(), randomRoom.getYCoord()+1)
                            || getNumberOfAdjacentRooms(randomRoom.getXCoord(), randomRoom.getYCoord()+1) >= 2) {
                        break;
                    } else {
                        map[randomRoom.getXCoord()][randomRoom.getYCoord() + 1] = nextRoom;
                        occupiedRooms.add(nextRoom);

                        break;
                    }

                case EAST:
                    nextRoom = new Room(randomRoom.getXCoord()+1, randomRoom.getYCoord(), NORMAL_ROOM);

                    if (roomIsOccupied(randomRoom.getXCoord()+1, randomRoom.getYCoord())
                            || getNumberOfAdjacentRooms(randomRoom.getXCoord()+1, randomRoom.getYCoord()) >= 2) {

                        break;
                    } else {

                        map[randomRoom.getXCoord() + 1][randomRoom.getYCoord()] = nextRoom;
                        occupiedRooms.add(nextRoom);

                        break;
                    }

                case SOUTH:
                    nextRoom = new Room(randomRoom.getXCoord(), randomRoom.getYCoord()-1, NORMAL_ROOM);

                    if (roomIsOccupied(randomRoom.getXCoord(), randomRoom.getYCoord()-1)
                            || getNumberOfAdjacentRooms(randomRoom.getXCoord(), randomRoom.getYCoord()-1) >= 2) {

                        break;
                    } else {

                        map[randomRoom.getXCoord()][randomRoom.getYCoord() - 1] = nextRoom;
                        occupiedRooms.add(nextRoom);

                        break;
                    }

                case WEST:
                    nextRoom = new Room(randomRoom.getXCoord()-1, randomRoom.getYCoord(), NORMAL_ROOM);

                    if (roomIsOccupied(randomRoom.getXCoord()-1, randomRoom.getYCoord())
                            || getNumberOfAdjacentRooms(randomRoom.getXCoord()-1, randomRoom.getYCoord()) >= 2) {

                        break;
                    } else {

                        map[randomRoom.getXCoord() - 1][randomRoom.getYCoord()] = nextRoom;
                        occupiedRooms.add(nextRoom);

                        break;
                    }
            }
        }
    }

    private void makeBossRoom() {
        Room bossRoom = getRandomOccupiedRoom();

        if ((getNumberOfAdjacentRooms(bossRoom.getXCoord(), bossRoom.getYCoord()) == 1)
                && !isAdjacentToSpecialRoom(bossRoom.getXCoord(), bossRoom.getYCoord())
                && !roomIsSpecialRoom(bossRoom.getXCoord(), bossRoom.getYCoord())) {
            bossRoom.setKind(BOSS_ROOM);
            containsBossRoom = true;
        }
    }



    private void makeVendorRoom() {
        Room vendorRoom = getRandomOccupiedRoom();

        if (getNumberOfAdjacentRooms(vendorRoom.getXCoord(), vendorRoom.getYCoord()) == 1
                && !isAdjacentToSpecialRoom(vendorRoom.getXCoord(), vendorRoom.getYCoord())
                && !roomIsSpecialRoom(vendorRoom.getXCoord(), vendorRoom.getYCoord())) {
            vendorRoom.setKind(VENDOR_ROOM);
            containsVendorRoom = true;
        }
    }

    private void makeItemRoom() {
        Room itemRoom = getRandomOccupiedRoom();

        if (getNumberOfAdjacentRooms(itemRoom.getXCoord(), itemRoom.getYCoord()) == 1
                && !isAdjacentToSpecialRoom(itemRoom.getXCoord(), itemRoom.getYCoord())
                && !roomIsSpecialRoom(itemRoom.getXCoord(), itemRoom.getYCoord())) {
            itemRoom.setKind(ITEM_ROOM);
            containsItemRoom = true;
        }
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
                        containsSecretRoom = true;
                    }
                }

                break;

            case EAST:
                if (map[randomRoom.getXCoord()+1][randomRoom.getYCoord()].getKind() == 0) {
                    if (getNumberOfAdjacentRooms(randomRoom.getXCoord()+1, randomRoom.getYCoord()) > 2) {
                        map[randomRoom.getXCoord()+1][randomRoom.getYCoord()].setKind(SECRET_ROOM);
                        containsSecretRoom = true;
                    }
                }
                break;

            case SOUTH:
                if (map[randomRoom.getXCoord()][randomRoom.getYCoord()-1].getKind() == 0) {
                    if (getNumberOfAdjacentRooms(randomRoom.getXCoord(), randomRoom.getYCoord()-1) > 2) {
                        map[randomRoom.getXCoord()][randomRoom.getYCoord()-1].setKind(SECRET_ROOM);
                        containsSecretRoom = true;
                    }
                }
                break;

            case WEST:
                if (map[randomRoom.getXCoord()-1][randomRoom.getYCoord()].getKind() == 0) {
                    if (getNumberOfAdjacentRooms(randomRoom.getXCoord()-1, randomRoom.getYCoord()) > 2) {
                        map[randomRoom.getXCoord()-1][randomRoom.getYCoord()].setKind(SECRET_ROOM);
                        containsSecretRoom = true;
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
        int adjacentRoomsCounter = 0;
        if (adjacentLeftOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentTopOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentRightOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        if (adjacentBottomOccupied(x, y)) {
            adjacentRoomsCounter++;
        }

        return adjacentRoomsCounter;
    }

    private boolean adjacentLeftOccupied(int x, int y) {
        return (map[x-1][y].getKind() > 0);
    }

    private boolean adjacentRightOccupied(int x, int y) {
        return (map[x+1][y].getKind() > 0);
    }

    private boolean adjacentTopOccupied(int x, int y) {
        return (map[x][y+1].getKind() > 0);
    }

    private boolean adjacentBottomOccupied(int x, int y) {
        return (map[x][y-1].getKind() > 0);
    }

    private boolean roomIsOccupied(int x, int y) {
        return (map[x][y].getKind() != 0);
    }

    private boolean roomIsSpecialRoom(int xCoord, int yCoord) {
        return (map[xCoord][yCoord].getKind() > 2);
    }

    private boolean isAdjacentToSpecialRoom(int x, int y) {
        return (map[x-1][y].getKind() > 2 && map[x-1][y].getKind() != 6 ||
                map[x][y+1].getKind() > 2 && map[x][y+1].getKind() != 6 ||
                map[x+1][y].getKind() > 2 && map[x+1][y].getKind() != 6 ||
                map[x][y-1].getKind() > 2 && map[x][y-1].getKind() != 6);
    }
}
