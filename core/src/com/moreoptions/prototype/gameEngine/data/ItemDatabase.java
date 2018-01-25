package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.callback.HitEvent;
import com.moreoptions.prototype.gameEngine.data.callback.PickupEvent;
import com.moreoptions.prototype.gameEngine.data.itemEffects.BlinkShot;
import com.moreoptions.prototype.gameEngine.data.itemEffects.KnockBack;
import com.moreoptions.prototype.gameEngine.util.EventFactory;
import com.moreoptions.prototype.gameEngine.util.ProjectileFactory;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by denwe on 07.01.2018.
 */
public class ItemDatabase {



    private static ItemDatabase ourInstance = new ItemDatabase();
    private ArrayList<Pair<Float, Item>>itemMap = new ArrayList<Pair<Float, Item>>();
    private ArrayList<Pair<Float, Item>>specialItemMap = new ArrayList<Pair<Float, Item>>();


    public static ItemDatabase getInstance() {
        return ourInstance;
    }

    private ItemDatabase() {

        registerItem(new Item("Full Heart", Color.CHARTREUSE, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {
                Statistics stats = e.getComponent(StatsComponent.class).getStats();
                if(stats.getCurrentHealth() + 1 < stats.getMaxHealth()) stats.setCurrentHealth(stats.getCurrentHealth() + 2);
                else stats.setCurrentHealth(stats.getMaxHealth());
                return true;
            }
        }), 20);


        registerItem(new Item("Gold Coin", Color.YELLOW, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {
                Statistics stats = e.getComponent(StatsComponent.class).getStats();
                if(stats.getMoney() + 1 < Consts.MAX_GOLD) stats.setMoney(stats.getMoney() + 1);
                else stats.setMoney(Consts.MAX_GOLD);
                return true;
            }
        }), 100);

        registerItem(new Item("Half Heart", Color.RED, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {
                Statistics stats = e.getComponent(StatsComponent.class).getStats();
                if(stats.getCurrentHealth() + 0.5f < stats.getMaxHealth()) stats.setCurrentHealth(stats.getCurrentHealth() + 1);
                else stats.setCurrentHealth(stats.getMaxHealth());
                return true;
            }
        }), 80);

        registerSpecialItem(new Item("Poison Heart", Color.OLIVE, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {
                Statistics stats = e.getComponent(StatsComponent.class).getStats();
                stats.setMaxHealth(1);
                stats.setCurrentHealth(1);
                stats.setFireRate(stats.getFireRate() - stats.getFireRate()/5);
                stats.setSpeed(stats.getSpeed()+50);
                stats.setDamage(stats.getDamage()+3);
                return true;
            }
        }), 10);

        registerSpecialItem(new Item("Firerate up", Color.BLUE, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {
                Statistics stats = e.getComponent(StatsComponent.class).getStats();
                if(stats.getFireRate() < 0.15f)
                stats.setFireRate(stats.getFireRate() - stats.getFireRate()/10);
                return true;
            }
        }), 10);

        registerSpecialItem(new Item("Knockback", Color.GRAY, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {
                Statistics statistics = e.getComponent(StatsComponent.class).getStats();
                statistics.setProjectileOnHit(new KnockBack());
                return true;
            }
        }), 100);

        registerSpecialItem(new Item("Blink Shot", Color.WHITE, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {
                Statistics statistics = e.getComponent(StatsComponent.class).getStats();
                statistics.setProjectileOnHit(new BlinkShot());
                return true;
            }
        }), 10);


        registerSpecialItem(new Item("Speed Up", Color.GREEN, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {
                Statistics stats = e.getComponent(StatsComponent.class).getStats();
                stats.setSpeed(stats.getSpeed() + 10);

                return true;
            }
        }), 100);

        registerSpecialItem(new Item("Damage Up", Color.SLATE, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {
                Statistics stats = e.getComponent(StatsComponent.class).getStats();
                stats.setDamage(stats.getDamage() + 1);
                return true;
            }
        }), 100);

        registerSpecialItem(new Item("Range Up", Color.MAROON, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {
                Statistics stats = e.getComponent(StatsComponent.class).getStats();
                stats.setRange(stats.getRange() + 40);
                return true;
            }
        }), 100);

        registerSpecialItem(new Item("Health Up", Color.RED, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {
                Statistics stats = e.getComponent(StatsComponent.class).getStats();
                stats.setMaxHealth(stats.getMaxHealth() + 1);
                return true;
            }
        }), 100);
    }

    
    public void registerItem(Item e, float chance) {


        Pair<Float, Item> p = new Pair(chance, e);
        itemMap.add(p);


    }

    public void registerSpecialItem(Item e, float chance) {


        Pair<Float, Item> p = new Pair(chance, e);
        specialItemMap.add(p);


    }
    
    
    public Entity generateItem(Room room, float x, float y) {
        Random r = new Random();

        ArrayList<Item> itemList = new ArrayList<Item>();
        int randomNumber = r.nextInt(100);
        for(Pair<Float, Item> entry : itemMap) {
            float f = entry.getKey();
            Item i = entry.getValue();

            if(randomNumber < f) {
                itemList.add(i);
            }

        }

        randomNumber = r.nextInt(itemList.size());

        return getItemEntity(itemList.get(randomNumber),room, x,y);

    }



    public Entity getItemEntity(Item itemBlueprint, Room room, float x, float y) {

        Entity itemEntity = new Entity();
        PositionComponent p = new PositionComponent(x,y);
        DebugCircleComponent dc = new DebugCircleComponent(5f);
        DebugColorComponent dcc = new DebugColorComponent(itemBlueprint.getColor());
        CircleCollisionComponent ccc = new CircleCollisionComponent(x,y ,5f);
        CollisionComponent cc = new CollisionComponent();
        PickupComponent pc = new PickupComponent(itemBlueprint.getName(),itemBlueprint.getPickupEvent(), room);


        pc.setPrice(itemBlueprint.getPrice());

        itemEntity.add(p).add(dc).add(dcc).add(pc).add(ccc).add(cc);


        return itemEntity;

    }

    public Entity getItemEntity(String name, Room room, float x, float y) {
        Item q = itemMap.get(0).getValue();
        for(Pair<Float, Item> p : itemMap) {
            if(p.getValue().getName().equals(name)) {
                q = p.getValue();
            }
        }
        return getItemEntity(q,room, x,y);
    }


    public Entity generateSpecialItem(float x, float y, Room room) {
        Random r = new Random();

        ArrayList<Item> itemList = new ArrayList<Item>();
        int randomNumber = r.nextInt(100);
        for(Pair<Float, Item> entry : specialItemMap) {
            float f = entry.getKey();
            Item i = entry.getValue();

            if(randomNumber < f) {
                itemList.add(i);
            }

        }

        randomNumber = r.nextInt(itemList.size());

        return getItemEntity(itemList.get(randomNumber),room, x,y);
    }

    public Entity generateShopItem(float x, float y, Room room) {
        Random r = new Random();

        ArrayList<Item> itemList = new ArrayList<Item>();
        int randomNumber = r.nextInt(100);
        for(Pair<Float, Item> entry : specialItemMap) {
            float f = entry.getKey();
            Item i = entry.getValue();

            if(randomNumber < f) {
                itemList.add(i);
            }

        }

        randomNumber = r.nextInt(itemList.size());

        Entity item = getItemEntity(itemList.get(randomNumber),room, x,y);
        PickupComponent p = item.getComponent(PickupComponent.class);
        p.setShopItem(true);
        return item;
    }

    public Entity generateGold(Room room, float x, float y) {
        return getItemEntity("Gold Coin", room, x, y);
    }
}
