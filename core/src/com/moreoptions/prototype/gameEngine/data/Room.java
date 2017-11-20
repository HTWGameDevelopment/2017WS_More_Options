package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.callback.PickupEvent;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;
import com.moreoptions.prototype.level.RoomBlueprint;
import com.moreoptions.prototype.level.RoomDefinition;

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


    ArrayList<Entity> entities;

    public Room(RoomBlueprint roomBlueprint) {

        Random r = new Random();
        ArrayList<RoomDefinition> roomlist = AssetLoader.getInstance().definition(roomBlueprint.isHasNeighbourTop(),roomBlueprint.isHasNeighbourBottom(),roomBlueprint.isHasNeighbourLeft(),roomBlueprint.isHasNeighbourRight());
        RoomDefinition rq = roomlist.get(r.nextInt(roomlist.size()));
        rq.getEntities();


    }

}
