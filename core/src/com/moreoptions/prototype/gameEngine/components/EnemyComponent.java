package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by denwe on 15.11.2017.
 */
public class EnemyComponent implements Component {

    private int startPosX;
    private int startPosY;
    private boolean dead;


    public boolean isDead() {
        return dead;
    }
}
