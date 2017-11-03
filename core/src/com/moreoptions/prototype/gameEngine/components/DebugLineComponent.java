package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * A component
 */
public class DebugLineComponent implements Component{

    private Vector2 start;
    private Vector2 end;

    public DebugLineComponent(Vector2 start, Vector2 end) {
        this.start = start;
        this.end = end;
    }

    public Vector2 getStart() {
        return start;
    }

    public void setStart(Vector2 start) {
        this.start = start;
    }

    public Vector2 getEnd() {
        return end;
    }

    public void setEnd(Vector2 end) {
        this.end = end;
    }
}
