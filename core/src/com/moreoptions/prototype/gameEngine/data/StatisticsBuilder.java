package com.moreoptions.prototype.gameEngine.data;

/**
 * Created by denwe on 10.01.2018.
 */
public class StatisticsBuilder {

    private float immunityTimer;
    private float timeSinceLastHit;
    private float deceleration;
    private float speed;
    private float damage;
    private float maxHealth;
    private float currentHealth;
    private float fireRate;
    private float projectileSpeed;
    private float range;
    private float currentShotCooldown;
    private int money;
    private int pushability;

    public StatisticsBuilder() {
        speed = 150;
        deceleration = 0.75f;
        maxHealth = 3;
        currentHealth = 3;
        fireRate = 1.5f;
        projectileSpeed = 120;
        pushability = 5;
        immunityTimer = 0;
        timeSinceLastHit = 2;
        range = 180;
        money = 0;
        damage = 1f;
        currentShotCooldown = fireRate;
    }

    public StatisticsBuilder speed(float f) {
        speed = f;
        return this;
    }
    public StatisticsBuilder deceleration(float f) {
        deceleration = f;
        return this;
    }
    public StatisticsBuilder maxHealth(float f) {
        maxHealth = f;
        currentHealth = maxHealth;
        return this;
    }
    public StatisticsBuilder firerate(float f) {
        fireRate = f;
        return this;
    }

    public StatisticsBuilder range(float f) {
        range = f;
        return this;
    }

    public StatisticsBuilder projectileSpeed(float f) {
        projectileSpeed = f;
        return this;
    }

    public Statistics build() {
        Statistics s = new Statistics();
        s.setSpeed(speed);
        s.setDeceleration(deceleration);
        s.setMaxHealth(maxHealth);
        s.setCurrentHealth(currentHealth);
        s.setFireRate(fireRate);
        s.setProjectileSpeed(projectileSpeed);
        s.setRange(range);
        return s;
    }
}