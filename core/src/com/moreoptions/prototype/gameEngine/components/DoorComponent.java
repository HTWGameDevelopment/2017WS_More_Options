package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.moreoptions.prototype.level.Offset;

/**
 * Created by denwe on 26.11.2017.
 */
public class DoorComponent implements Component {

    private boolean open = false;
    private Offset offset;

    public static final boolean DOOR_OPEN = true;
    public static final boolean DOOR_FALSE = false;
    private boolean state;

    public DoorComponent(Offset offset) {
        this.offset = offset;
    }

    public void toggle() {

        open = !open;
    }

    public Offset getOffset() {
        return offset;
    }


    public void setState(boolean state) {
        this.state = state;
    }
}
