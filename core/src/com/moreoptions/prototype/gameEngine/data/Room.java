package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.callback.ChangeRoomEvent;
import com.moreoptions.prototype.gameEngine.exceptions.MissdefinedTileException;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;
import com.moreoptions.prototype.level.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Rooms are: 15x9, consist of 2 maps. This is just a prototype implementation with everything hardcoded.
 *
 * 1 walls
 * 1 entities
 *
 *
 * Ex:  {
 *          {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
 *          {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 *          {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 *          {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 *          {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 *          {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 *          {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 *          {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 *          {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
 *      }
 *      
 *      {
 *          {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,5,5,5,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,5,5,5,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,5,5,5,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
 *      }
 *
 */
public class Room {

    DestructibleLayer destLayer;
    TileLayer tileLayer;
    private Room leftNeighbour;
    private Room rightNeighbour;
    private Room topNeighbour;
    private Room bottomNeighbour;
    private int x;
    private int y;
    private int id;

    public Room(RoomBlueprint roomBlueprint) {

        x = roomBlueprint.getXCoord();
        y = roomBlueprint.getYCoord();

        Random r = new Random();

        //TODO refactor this!
        ArrayList<RoomDefinition> roomlist = AssetLoader
                .getInstance()
                .definition(roomBlueprint.isHasNeighbourTop(),roomBlueprint.isHasNeighbourBottom(),roomBlueprint.isHasNeighbourLeft(),roomBlueprint.isHasNeighbourRight());

        RoomDefinition rq = roomlist.get(r.nextInt(roomlist.size()));

        try {
            destLayer = rq.getDestLayer();
            tileLayer = rq.getTileLayer();
        } catch (MissdefinedTileException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Entity> getEntities() {

        ArrayList<Entity> entities = new ArrayList<Entity>();
        entities.addAll(destLayer.getEntities());
        entities.addAll(tileLayer.getEntities());

        if(leftNeighbour != null) entities.add(createDoor(1,6,leftNeighbour, Offset.LEFT));
        if(rightNeighbour != null) entities.add(createDoor(16,6,rightNeighbour, Offset.RIGHT));
        if(topNeighbour != null) entities.add(createDoor(8,10,topNeighbour, Offset.TOP));
        if(bottomNeighbour != null) entities.add(createDoor(8,0,bottomNeighbour, Offset.DOWN));

        return entities;
    }

    private Entity createDoor(int x, int y, Room room, Offset offset) {
        Entity e = new Entity();

        e.add(new PositionComponent(x * Consts.TILE_SIZE, y * Consts.TILE_SIZE));
        e.add(new CollisionComponent(new ChangeRoomEvent(room)));
        e.add(new DoorComponent(offset));
        e.add(new CircleCollisionComponent(x * Consts.TILE_SIZE, y * Consts.TILE_SIZE, Consts.TILE_SIZE/2));

        return e;

    }

    public void setLeftNeighbour(Room leftNeighbour) {
        this.leftNeighbour = leftNeighbour;
    }

    public void setRightNeighbour(Room rightNeighbour) {
        this.rightNeighbour = rightNeighbour;
    }

    public void setTopNeighbour(Room topNeighbour) {
        this.topNeighbour = topNeighbour;
    }

    public void setBottomNeighbour(Room bottomNeighbour) {
        this.bottomNeighbour = bottomNeighbour;
    }

    public Room getLeftNeighbour() {
        System.out.print(x + "| " + y);
        return leftNeighbour;
    }

    public Room getRightNeighbour() {
        return rightNeighbour;
    }

    public Room getTopNeighbour() {
        return topNeighbour;
    }

    public Room getBottomNeighbour() {
        return bottomNeighbour;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
