package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.Statistics;

/**
 * Created by denwe on 15.11.2017.
 */
public class EnemyComponent implements Component {

    private Vector2 startPosition;
    private boolean dead;

    private Room room;

    private float health = 3;
    private float currentHealth = 3;

    public EnemyComponent(float x, float y,Room room) {
        startPosition = new Vector2(x,y);
        this.room = room;
    }

    public boolean isDead() {
        return dead;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(float currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Room getRoom() {
        return room;
    }

}
