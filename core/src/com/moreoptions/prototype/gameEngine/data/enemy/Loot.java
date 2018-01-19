package com.moreoptions.prototype.gameEngine.data.enemy;

import com.moreoptions.prototype.gameEngine.data.Item;
import com.moreoptions.prototype.gameEngine.data.ItemDatabase;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventSubscriber;

import java.util.ArrayList;

public class Loot {
    private EventSubscriber subscriber = new EventSubscriber();


    private final static ArrayList<Item> stdLoot = stdLootList();

    public static ArrayList<Item> getLootById(int id){

        ArrayList<Item> lootList = new ArrayList<Item>();

        switch(id){
            default:
                return stdLoot;
        }

    }

    public static ArrayList<Item> stdLootList(){
        ItemDatabase idata = ItemDatabase.getInstance();

        ArrayList<Item> items = new ArrayList<Item>();
        items.add(idata.getItemByName("Full Heart"));
        items.add(idata.getItemByName("Gold Coin"));
        items.add(idata.getItemByName("Half Heart"));
        items.add(idata.getItemByName("Speed Up"));

        return items;

    }
}
