package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by denwe on 26.11.2017.
 */
public class DoorComponent implements Component {

    boolean open = false;

    public DoorComponent() {

    }

    public void toggle() {

        open = !open;
    }

}
