package com.moreoptions.prototype.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.moreoptions.prototype.gameEngine.components.EnemyComponent;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.util.EnemyFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Dennis on 07.12.2017.
 */
public class EnemyLayer implements Layer {

    ArrayList<Entity> enemies = new ArrayList<Entity>();

    public void addEnemy(int id, float x, float y, Room room) {



        enemies.add(EnemyFactory.createEnemy(id, x, y,room));
    }

    @Override
    public ArrayList<Entity> getEntities() {



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
}
