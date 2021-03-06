package com.moreoptions.prototype.gameEngine.level.layers;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by denwe on 21.11.2017.
 */
public class DestructibleLayer {

    private Entity[][] destructibles;

    public DestructibleLayer(Entity[][] destructibles, int width, int height) {
        this.destructibles = destructibles;
    }

    public ArrayList<Entity> getEntities() {
        ArrayList list = new ArrayList();
        for(Entity[] e: destructibles ) {
            for(Entity ex : Arrays.asList(e)) {
                if(ex != null) list.add(ex);
            }
        }
        return list;
    }

}
