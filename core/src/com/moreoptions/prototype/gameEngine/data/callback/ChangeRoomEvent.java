package com.moreoptions.prototype.gameEngine.data.callback;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Game;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.DoorComponent;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.level.Offset;

/**
 * Created by denwe on 26.11.2017.
 */
public class ChangeRoomEvent implements CollisionEvent {

    private Room room;

    public ChangeRoomEvent(Room room) {
        this.room = room;
    }

    @Override
    public boolean onCollision(Entity us, Entity them) {
        Offset offset = them.getComponent(DoorComponent.class).getOffset();
        System.out.println(room.getEntities().size());
        GameWorld.getInstance().getRoomManager().changeRoom(room, offset);

        return false;
    }
}
