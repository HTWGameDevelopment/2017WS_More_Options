package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by denwe on 30.10.2017.
 */
public class TileComponent implements Component {

    private int id;
    private boolean collidable;

    public TileComponent(int id, boolean collidible) {
        this.id = id;
        this.collidable = collidible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public void setCollidable(boolean collidible) {
        this.collidable = collidible;
    }
}
