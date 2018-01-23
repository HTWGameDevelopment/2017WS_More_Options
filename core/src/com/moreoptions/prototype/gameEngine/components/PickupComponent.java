package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.callback.PickupEvent;

/**
 * Component for PickUps
 */
public class PickupComponent implements Component {

    private PickupEvent event;
    private Room room;
    private String name;
    private boolean shopItem;
    private int price;

    public PickupComponent(String name, PickupEvent event, Room room) {
        this.event = event;
        this.room = room;
        this.name = name;
    }

    public boolean trigger(Entity e) {
        return event.onPickup(e);
    }


    public Room getRoom() {
        return room;
    }

    public String getName() {
        return name;
    }

    public boolean isShopItem() {
        return shopItem;
    }

    public int getPrice() {
        return price;
    }

    public void setShopItem(boolean shopItem) {
        this.shopItem = shopItem;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
