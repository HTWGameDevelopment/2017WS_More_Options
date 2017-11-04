package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

/**
 * Component that holds color-information
 */
public class DebugColorComponent implements Component {

    public DebugColorComponent(Color color) {
        this.color = color;
    }

    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
