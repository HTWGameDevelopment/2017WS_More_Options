package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.callback.HitEvent;
import com.moreoptions.prototype.gameEngine.data.callback.PickupEvent;
import com.moreoptions.prototype.gameEngine.util.EventFactory;
import com.moreoptions.prototype.gameEngine.util.ProjectileFactory;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by denwe on 07.01.2018.
 */
public class ItemDatabase {

    private ComponentMapper<StatsComponent> statsMapper = ComponentMapper.getFor(StatsComponent.class);
    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);


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
                if(stats.getCurrentHealth() + 1 < stats.getMaxHealth()) stats.setCurrentHealth(stats.getCurrentHealth() + 1);
                else stats.setCurrentHealth(stats.getMaxHealth());
                return true;
            }
        }), 50);

        registerItem(new Item("Poison Heart", Color.OLIVE, new PickupEvent() {
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

        registerItem(new Item("Firerate up", Color.BLUE, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {

                Statistics stats = e.getComponent(StatsComponent.class).getStats();
                if(stats.getFireRate() < 0.15f)
                stats.setFireRate(stats.getFireRate() - stats.getFireRate()/10);
                return true;
            }
        }), 10);

        registerItem(new Item("Knockback", Color.GRAY, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {

                Statistics statistics = e.getComponent(StatsComponent.class).getStats();

                statistics.setProjectileOnHit(new HitEvent() {
                    @Override
                    public boolean onHit(Entity self, Entity hit) {

                        Entity p = self.getComponent(ProjectileComponent.class).getOwner();

                        PositionComponent temp1 = posMapper.get(p);
                        PositionComponent temp2 = posMapper.get(hit);
                        float xEnm = temp2.getX();
                        float yEnm = temp2.getY();
                        float xPla = temp1.getX();
                        float yPla = temp1.getY();
                        Vector2 v2 = new Vector2(xEnm-xPla,yEnm-yPla);
                        v2 = v2.nor();

                        hit.getComponent(DisplacableComponent.class).applyForce(v2, 6);


                        EventFactory.projectileHit(self,hit);

                        return true;
                    }
                });

                return true;
            }
        }), 100);

        registerItem(new Item("Multi Shot", Color.WHITE, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {

                Statistics statistics = e.getComponent(StatsComponent.class).getStats();

                statistics.setProjectileOnHit(new HitEvent() {
                    @Override
                    public boolean onHit(Entity self, Entity hit) {

                        if(statsMapper.has(hit)) {

                            Entity p = self.getComponent(ProjectileComponent.class).getOwner();



                            PositionComponent temp1 = posMapper.get(p);
                            PositionComponent temp2 = posMapper.get(hit);
                            Vector2 v1 = temp1.getPosition().cpy();
                            Vector2 v2 = temp2.getPosition().cpy();

                            EventFactory.projectileHit(self,hit);
                            if(hit.getComponent(StatsComponent.class).getStats().getCurrentHealth() < 1) {

                                temp1.setPosition(v2);
                                temp2.setPosition(v1);
                            }
                        } else {


                            return false;
                        }
                        return true;
                    }
                });


                return true;
            }
        }), 10);

        registerItem(new Item("Gold Coin", Color.YELLOW, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {

                Statistics stats = e.getComponent(StatsComponent.class).getStats();
                if(stats.getMoney() + 1 < Consts.MAX_GOLD) stats.setMoney(stats.getMoney() + 1);
                else stats.setMoney(Consts.MAX_GOLD);
                return true;
            }
        }), 10);

        registerItem(new Item("Half Heart", Color.RED, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {

                Statistics stats = e.getComponent(StatsComponent.class).getStats();
                if(stats.getCurrentHealth() + 0.5f < stats.getMaxHealth()) stats.setCurrentHealth(stats.getCurrentHealth() + 0.5f);
                else stats.setCurrentHealth(stats.getMaxHealth());
                return true;
            }
        }), 80);

        registerSpecialItem(new Item("Speed Up", Color.GREEN, new PickupEvent() {
            @Override
            public boolean onPickup(Entity e) {

                Statistics stats = e.getComponent(StatsComponent.class).getStats();
                stats.setSpeed(stats.getSpeed() + 1);

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
        PickupComponent pc = new PickupComponent(itemBlueprint.getPickupEvent(), room);

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
}
