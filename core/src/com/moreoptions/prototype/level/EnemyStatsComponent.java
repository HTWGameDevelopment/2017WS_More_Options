package com.moreoptions.prototype.level;

import com.badlogic.ashley.core.Component;

/**
 * Created by Dennis on 06.12.2017.
 */
public class EnemyStatsComponent implements Component {

    float maxHealth;
    float currentHealth;

    public EnemyStatsComponent(float maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }



}
