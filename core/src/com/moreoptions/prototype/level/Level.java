package com.moreoptions.prototype.level;

import com.moreoptions.prototype.gameEngine.data.Room;

/**
 * Created by denwe on 19.11.2017.
 */
public class Level {

    private Room[][] rooms;
    private Room startingRoom;

    public Level(Room[][] test, Room startingRoom) {
        this.rooms = test;
        this.startingRoom = startingRoom;
    }

    public Room getStartingRoom() {
        return startingRoom;
    }
}
