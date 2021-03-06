package com.moreoptions.prototype.gameEngine.level.layers;

import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.components.EnemyComponent;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.ItemDatabase;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.util.EnemyFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Dennis on 07.12.2017.
 */
public class EnemyLayer implements Layer {

    ArrayList<Entity> enemies = new ArrayList<Entity>();
    ArrayList<Entity> items = new ArrayList<Entity>();

    public void addEnemy(int id, float x, float y, Room room) {
        enemies.add(EnemyFactory.createEnemy(id, x, y,room));
    }

    @Override
    public ArrayList<Entity> getEntities() {
        ArrayList<Entity> entites = new ArrayList<Entity>();
        entites.addAll(enemies);
        entites.addAll(items);
        return enemies;
    }

    public Collection<? extends Entity> getAliveEntities() {
        ArrayList<Entity> aliveEnemies = new ArrayList<Entity>();
        for(Entity e : enemies) {
            EnemyComponent ec = e.getComponent(EnemyComponent.class);
            if(!ec.isDead()) aliveEnemies.add(e);
        }
        return aliveEnemies;
    }

    public void addItem(int id, float x, float y, Room room) {
        if(id == Consts.SPECIAL_ITEM) {
            room.addItem(ItemDatabase.getInstance().generateSpecialItem(x,y,room));
        } else {
            room.addItem(ItemDatabase.getInstance().generateItem(room, x, y));
        }
    }

    public void addShopItem(int id,float x, float y, Room room) {
        room.addItem(ItemDatabase.getInstance().generateShopItem(x,y,room));
    }

    public void addSpawnedEnemy(Entity e) {
        enemies.add(e);
    }

    public void removeItem(Entity e) {
        items.remove(e);
    };

    public ArrayList<Entity> getItems() {
        return items;
    }
}
