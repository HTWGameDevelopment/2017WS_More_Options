package com.moreoptions.prototype.level;

import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;

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

        Room[][] generatedRooms = generateRoom(rooms, width,height);


        return null;
    }


    private Room[][] generateRoom(RoomBlueprint[][] rooms, int width, int height) {
        System.out.println("TEST");

        Room[][] generatedRooms = new Room[width][height];
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                generatedRooms[x][y] = new Room(rooms[x][y]);
            }
        }

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(rooms[x][y].isHasNeighbourLeft()) {
                    generatedRooms[x][y].setLeftNeighbour(generatedRooms[x-1][y]);
                } if(rooms[x][y].isHasNeighbourRight()) {
                    generatedRooms[x][y].setRightNeighbour(generatedRooms[x+1][y]);
                } if(rooms[x][y].isHasNeighbourTop()) {
                    generatedRooms[x][y].setTopNeighbour(generatedRooms[x][y+1]);
                } if(rooms[x][y].isHasNeighbourBottom()) {
                    generatedRooms[x][y].setBottomNeighbour(generatedRooms[x][y-1]);
                }
            }
        }


        return null;
    }


}
