package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by denwe on 15.11.2017.
 */
public class EnemyComponent implements Component {

    private int startPosX;
    private int startPosY;
    private boolean dead;

    private float health = 3;
    private float currentHealth = 3;

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
}
