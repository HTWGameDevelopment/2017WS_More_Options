package com.moreoptions.prototype.level;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;

/**
 * Created by Dennis on 07.12.2017.
 */
public class EnemyLayer implements Layer {

    ArrayList<Entity> enemies;

    public void addEnemy(int id, float x, float y) {

        //enemies.add(EnemyFactory.getEnemy(id, x, y));
    }

    @Override
    public ArrayList<Entity> getEntities() {



        return enemies;
    }
}
