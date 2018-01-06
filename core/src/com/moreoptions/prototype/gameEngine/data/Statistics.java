package com.moreoptions.prototype.gameEngine.data;

/**
 *
 */

public class Statistics {

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
    private int pushability;

    public Statistics() {
        this.speed = 150;
        this.deceleration = 0.75f;
        this.maxHealth = 3;
        this.currentHealth = 3;

        this.fireRate = 1.5f;
        this.projectileSpeed = 120;
        this.pushability = 5;
        this.immunityTimer = 2;
        this.timeSinceLastHit = 0;

        this.range = 100;

        this.damage = 1.5f;
        this.currentShotCooldown = fireRate;

    }

    public float getDeceleration() {
        return deceleration;
    }

    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(float currentHealth) {
        this.currentHealth = currentHealth;
    }

    public float getFireRate() {
        return fireRate;
    }

    public void setFireRate(float fireRate) {
        this.fireRate = fireRate;
    }

    public float getProjectileSpeed() {
        return projectileSpeed;
    }

    public void setProjectileSpeed(float projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getCurrentShotCooldown() {
        return currentShotCooldown;
    }

    public float getImmunityTimer() {
        return immunityTimer;
    }

    public void setImmunityTimer(float immunityTimer) {
        this.immunityTimer = immunityTimer;
    }

    public float getTimeSinceLastHit() {
        return timeSinceLastHit;
    }

    public void setTimeSinceLastHit(float timeSinceLastHit) {
        this.timeSinceLastHit = timeSinceLastHit;
    }

    public void setPushability(int pushability) {
        this.pushability = pushability;
    }

    public void setCurrentShotCooldown(float currentShotCooldown) {
        this.currentShotCooldown = currentShotCooldown;
    }

    public int getPushability() {
        return pushability;
    }
}
