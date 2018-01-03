package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;

public class DebugSquareComponent implements Component {

    private float size;


    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public DebugSquareComponent(float size) {
        this.size = size;
    }


}
