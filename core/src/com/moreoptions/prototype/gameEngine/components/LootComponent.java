package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Family;
import com.moreoptions.prototype.gameEngine.data.Item;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LootComponent implements Component {



    private ArrayList<Item> lootList;

    public LootComponent (Item item){
        this.lootList = new ArrayList<Item>();
        lootList.add(item);
    }
    
    public LootComponent (ArrayList<Item> itemList){
        this.lootList = itemList;
    }
    
    public void addItem(Item item){
        this.lootList.add(item);
    }
    
    public void clearLoot(){
        this.lootList.clear();
    }

    public ArrayList<Item> getLootList() {
        return lootList;
    }

    public void setLootList(ArrayList<Item> lootList) {
        this.lootList = lootList;
    }

   /* public int size(ArrayList<Item> lootList){
        int size = 0;
        for (int i = 0;i< lootList.size();i++){
            size++;
        }
        return size;
    }*/
}
