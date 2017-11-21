package com.moreoptions.prototype.level;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by denwe on 21.11.2017.
 */
public class TileLayer {

    Entity[][] tiles;

    public TileLayer(Entity[][] tiles, int width, int height) {

        this.tiles = tiles;

    }


    /*
Height: 11, Width: 17.

         A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q

     A   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     B   0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,
     C   0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,
     D   0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,
     E   0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,
     F   0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,
     G   0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,
     H   0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,
     I   0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,
     J   0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,
     K   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,

 */


    public void addDoorLeft() {

    /*                             EB
    // We need to edit tile at  FA FB
    //                             GB

        FA has to become the teleport the the left room.
        FB has to become a closedTile.

    */

    }

    public void openAllClosedTiles() {

        // Take all door-tile-entities that are closed. Change their state to open.

    }

    public ArrayList<Entity> getEntities() {
        ArrayList list = new ArrayList();
        for(Entity[] e: tiles ) {
            for(Entity ex : Arrays.asList(e)) {
                if(ex != null) list.add(ex);
            }
        }
        return list;
    }


}




