package com.moreoptions.prototype.level.layers;

import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.level.layers.Layer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by denwe on 21.11.2017.
 */
public class TileLayer implements Layer {

    private Entity[][] tiles;

    public TileLayer(Entity[][] tiles, int width, int height) {

        this.tiles = tiles;

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




