package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.gdx.graphics.Color;
import com.moreoptions.prototype.gameEngine.data.callback.PickupEvent;
import sun.security.util.ObjectIdentifier;

/**
 * Created by denwe on 07.01.2018.
 */
public class Item {

    private String name;
    private Color color;
    private PickupEvent pickupEvent;

    public Item(String name, Color color, PickupEvent pickupEvent) {
        this.name = name;
        this.color = color;
        this.pickupEvent = pickupEvent;
    }

    public Color getColor() {
        return color;
    }

    public PickupEvent getPickupEvent() {
        return pickupEvent;
    }

    public String getName() {
        return name;
    }

    /*
    public class ItemBuilder {


        PickupEvent  pickupEvent;


    }
     */
}


