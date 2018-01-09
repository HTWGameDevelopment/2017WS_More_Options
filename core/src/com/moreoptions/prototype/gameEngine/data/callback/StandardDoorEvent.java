package com.moreoptions.prototype.gameEngine.data.callback;

import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.level.Offset;

/**
 * Created by denwe on 25.11.2017.
 */
public class StandardDoorEvent implements DoorEvent {

    private Room room;
    private Offset offset;

    public StandardDoorEvent(Room rOom, Offset offset) {
        this.room = rOom;
        this.offset = offset;
    }


    @Override
    public boolean onTouch(Room room) {

        GameWorld.getInstance().getRoomManager().changeRoom(room,offset);

        return true;
    }
}
