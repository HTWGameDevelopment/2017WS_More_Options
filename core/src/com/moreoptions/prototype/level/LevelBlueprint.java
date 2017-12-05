package com.moreoptions.prototype.level;

import com.moreoptions.prototype.gameEngine.data.Room;

/**
 * Created by denwe on 04.11.2017.
 */
public class LevelBlueprint{

    private RoomBlueprint[][] rooms;
    private int width;
    private int height;

    public LevelBlueprint(RoomBlueprint[][] rooms, int width, int height) {
        this.rooms = rooms;
        this.width = width;
        this.height = height;
    }

    public Level generateLevel() {
        Room startingRoom = null;

        Room[][] generatedRooms = new Room[width][height];
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                System.out.print(String.format("%1$"+4+ "s", String.valueOf(rooms[x][y].getKind())));
                if(rooms[x][y].getKind() == RoomBlueprint.EMPTY_ROOM) continue;
                generatedRooms[x][y] = new Room(rooms[x][y]);
                if(rooms[x][y].getKind() ==RoomBlueprint.STARTING_ROOM) {
                    startingRoom = generatedRooms[x][y];
                }
            }

            System.out.println();
        }
        System.out.println();

        int count = 0;
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(generatedRooms[x][y] == null) continue;
                generatedRooms[x][y].setId(count++);
                if(rooms[x][y].isHasNeighbourLeft()) {
                    if(isValid(width,height,x,y-1))
                    generatedRooms[x][y].setLeftNeighbour(generatedRooms[x][y-1]);
                } if(rooms[x][y].isHasNeighbourRight()) {
                    if(isValid(width,height,x,y+1)) generatedRooms[x][y].setRightNeighbour(generatedRooms[x][y+1]);
                } if(rooms[x][y].isHasNeighbourTop()) {
                    if(isValid(width,height,x-1, y))
                    generatedRooms[x][y].setTopNeighbour(generatedRooms[x-1][y]);
                } if(rooms[x][y].isHasNeighbourBottom()) {
                    if(isValid(width,height,x+1,y))
                    generatedRooms[x][y].setBottomNeighbour(generatedRooms[x+1][y]);
                }
            }

            System.out.println();
        }


        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(rooms[x][y].getKind() != RoomBlueprint.EMPTY_ROOM) System.out.print(String.format("%1$"+4+ "s", String.valueOf(generatedRooms[x][y].getId())));
                else System.out.print(String.format("%1$"+4+ "s", "0"));
            }
            System.out.println();
        }
        return new Level(generatedRooms, startingRoom);
    }

    private boolean isValid(int width, int height, int x, int y) {
        return !(x>=width || y>=height || x <0 || y < 0);
    }


}
